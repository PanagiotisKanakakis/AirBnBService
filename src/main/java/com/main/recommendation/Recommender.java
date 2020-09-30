package com.main.recommendation;

import com.main.dao.ResidenceRepository;
import com.main.dao.UserRepository;
import com.main.entity.ResidenceEntity;
import com.main.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.*;

@Component
public class Recommender {

    @Autowired
    UserRepository userRepository;
    @Autowired
    ResidenceRepository residenceRepository;

    List<ResidenceEntity> residenceEntities;
    List<UserEntity> userEntities;

    Map<Integer, String> arrayToUser = new HashMap<>();
    Map<Integer, Integer> arrayToResidence = new HashMap<>();
    Map<Integer, Integer> residenceToArray = new HashMap<>();
    Map<String, Integer> userToArray = new HashMap<>();


    Pair<float[][], float[][]> p;

    public static class Pair<A, B> {
        public final A a;
        public final B b;

        public Pair(A a, B b) {
            this.a = a;
            this.b = b;
        }
    }

    @PostConstruct
    public void init(){
        float[][] matrix = CreateMatrix();
        this.p = myRecommender(matrix, 5, 0.001f, 0.1f);
    }

    public List<ResidenceEntity> run(String username) {
        float[][] predicted = PredictRating(p.a, p.b);
        int userpos = this.userToArray.get(username);

        float[] residences = predicted[userpos];
        List<Pair<Integer,Float>> rs = new ArrayList<>();
        for(int j=0;j<this.residenceEntities.size();j++)
            rs.add(new Pair<>(this.residenceEntities.get(j).getResidenceId(), residences[j]));

        rs.sort(Comparator.comparing(p -> -p.b));
        List<ResidenceEntity> resultSet = new ArrayList<>();
        rs.stream()
                .skip(0)
                .limit(5).forEach(residenceEntityFloatPair -> resultSet.add(residenceRepository.findById(residenceEntityFloatPair.a).get()));
        return  resultSet;
    }

    public float[][] CreateMatrix() {

        this.residenceEntities = residenceRepository.findAllByOrderByResidenceId();
        this.userEntities = userRepository.findAllByOrderByUsername();

        int users = userEntities.size();
        int residences = residenceEntities.size();

        float[][] matrix = new float[users][residences];
        for (int i = 0; i < matrix.length; ++i) {
            for (int j = 0; j < matrix[0].length; ++j) {
                matrix[i][j] = -1f;
            }
        }

        for(int i=0;i<residences;i++){
            residenceToArray.put(this.residenceEntities.get(i).getResidenceId(),i);
            arrayToResidence.put(i,this.residenceEntities.get(i).getResidenceId());
        }

        for(int i=0;i<users;i++){
            userToArray.put(this.userEntities.get(i).getUsername(),i);
            arrayToUser.put(i,this.userEntities.get(i).getUsername());
        }

        userEntities.forEach(userEntity -> {
            /* get residences that user made a reservation */
            userEntity.getReservedResidences().forEach(reservationEntity -> {
                ResidenceEntity residenceEntity = reservationEntity.getResidenceEntity();
                int userArrayPosition = this.userToArray.get(userEntity.getUsername());
                int residenceArrayPosition = this.residenceToArray.get(residenceEntity.getResidenceId());
                matrix[userArrayPosition][residenceArrayPosition] = 1;

                residenceEntity.getComments().forEach(commentEntity -> {
                    UserEntity user = commentEntity.getUser();
                    int userpos = this.userToArray.get(user.getUsername());
                    matrix[userpos][residenceArrayPosition] = 1;
                });

            });
        });
        return matrix;
    }

    public Pair<float[][], float[][]> myRecommender(float[][] matrix, int r, float rate, float lambda) {
        int maxIter = 1000;
        int n1 = matrix.length;
        int n2 = matrix[0].length;

        float[][] U = new float[n1][r];
        float[][] V = new float[n2][r];

        // Initialize U and V matrix
        Random rand = new Random();
        for (int i = 0; i < U.length; ++i) {
            for (int j = 0; j < U[0].length; ++j) {
                U[i][j] = rand.nextFloat() / (float) r;
            }
        }

        for (int i = 0; i < V.length; ++i) {
            for (int j = 0; j < V[0].length; ++j) {
                V[i][j] = rand.nextFloat() / (float) r;
            }
        }


        // Gradient Descent
        for (int iter = 0; iter < maxIter; ++iter) {
//			System.out.println("Iteration no. " + iter + " / " + maxIter);

            float[][] prodMatrix = new float[n1][n2];
            for (int i = 0; i < n1; ++i) {
                for (int j = 0; j < n2; ++j) {
                    for (int k = 0; k < r; ++k) {
                        prodMatrix[i][j] += U[i][k] * V[j][k];
                    }
                }
            }

            float[][] errorMatrix = new float[n1][n2];
            for (int i = 0; i < n1; ++i) {
                for (int j = 0; j < n2; ++j) {
                    if (matrix[i][j] == -1f) {
                        errorMatrix[i][j] = 0f;
                    } else {
                        errorMatrix[i][j] = matrix[i][j] - prodMatrix[i][j];
                    }
                }
            }

            float[][] UGrad = new float[n1][r];
            for (int i = 0; i < n1; ++i) {
                for (int j = 0; j < r; ++j) {
                    for (int k = 0; k < n2; ++k) {
                        UGrad[i][j] += errorMatrix[i][k] * V[k][j];
                    }
                }
            }

            float[][] VGrad = new float[n2][r];
            for (int i = 0; i < n2; ++i) {
                for (int j = 0; j < r; ++j) {
                    for (int k = 0; k < n1; ++k) {
                        VGrad[i][j] += errorMatrix[k][i] * U[k][j];
                    }
                }
            }

            float[][] Un = new float[n1][r];
            for (int i = 0; i < n1; ++i) {
                for (int j = 0; j < r; ++j) {
                    Un[i][j] = (1f - rate * lambda) * U[i][j] + rate * UGrad[i][j];
                }
            }

            float[][] Vn = new float[n2][r];
            for (int i = 0; i < n2; ++i) {
                for (int j = 0; j < r; ++j) {
                    Vn[i][j] = (1f - rate * lambda) * V[i][j] + rate * VGrad[i][j];
                }
            }

            U = Un;
            V = Vn;
        }

        Pair<float[][], float[][]> p = new Pair<>(U, V);
        return p;
    }

    public float[][] PredictRating(float[][] U, float[][] V) {
        int n1 = U.length;
        int n2 = V.length;
        int r = V[0].length;

        float[][] prodMatrix = new float[n1][n2];
        for (int i = 0; i < n1; ++i) {
            for (int j = 0; j < n2; ++j) {
                for (int k = 0; k < r; ++k) {
                    prodMatrix[i][j] += U[i][k] * V[j][k];
                }
            }
        }
        return prodMatrix;
    }

}
