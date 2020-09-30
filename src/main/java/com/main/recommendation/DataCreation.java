package com.main.recommendation;

import com.main.dto.residence.AddCommentToResidenceDto;
import com.main.dto.residence.AddResidenceRequestDto;
import com.main.dto.user.RoleDto;
import com.main.dto.user.UserRegisterRequestDto;
import com.main.service.ResidenceServiceApi;
import com.main.service.UserServiceApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class DataCreation {

    @Autowired
    UserServiceApi userServiceApi;
    @Autowired
    ResidenceServiceApi residenceServiceApi;

    @Autowired
    Recommender recommender;

    public void init() {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(getClass().getClassLoader().getResource("data/listings.csv").getFile()));
            String row;
            while ((row = br.readLine()) != null) {
                System.out.println(row);
                List<String> items = Arrays.asList(row.split("\\s*\t\\s*"));
                UserRegisterRequestDto user = createUser(items);

                try{
                    userServiceApi.register(user);
                }catch (Exception e){
//                    e.printStackTrace();
                }
                try{
                    AddResidenceRequestDto residence = createResidence(items);
                    residenceServiceApi.addResidence(residence);
                }catch (Exception e){
//                    e.printStackTrace();
                }
            }

            br = new BufferedReader(new FileReader(getClass().getClassLoader().getResource("data/clean_reviews.csv").getFile()));
            while ((row = br.readLine()) != null) {
                List<String> items = Arrays.asList(row.split("\\s*\t\\s*"));
                System.out.println(items);
                AddCommentToResidenceDto addCommentToResidenceDto = createResidenceComment(items);
                try {
                    residenceServiceApi.addComment(addCommentToResidenceDto);
                }catch (Exception e){

                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private AddCommentToResidenceDto  createResidenceComment(List<String> items) throws Exception {
        AddCommentToResidenceDto addCommentToResidenceDto = new AddCommentToResidenceDto();
        if(items.get(5) == null)
            throw new Exception();
        addCommentToResidenceDto.setComment(items.get(5));
        addCommentToResidenceDto.setUsername(items.get(3));
        addCommentToResidenceDto.setResidenceId(Integer.parseInt(items.get(0)));
        return addCommentToResidenceDto;
    }

    private AddResidenceRequestDto createResidence(List<String> items) {
        AddResidenceRequestDto residence = new AddResidenceRequestDto();
        residence.setAddress(items.get(14));
        residence.setBathrooms((int) Double.parseDouble(items.get(29)));
        residence.setBedrooms((int) Double.parseDouble(items.get(30)));
        residence.setBeds((int) Double.parseDouble(items.get(30)));
        residence.setCapacity((int) Double.parseDouble(items.get(35)));
        residence.setDescription(items.get(1));
        residence.setTitle(items.get(2));
        residence.setSize((double) 100);
        residence.setPrize((int) Double.parseDouble(items.get(32)));
        residence.setGeoX(Double.parseDouble(items.get(22)));
        residence.setGeoY(Double.parseDouble(items.get(23)));
        residence.setLocation(items.get(6));
        residence.setType(items.get(26));
        residence.setWifi((int) Double.parseDouble(items.get(78)));
        residence.setElevator((int) Double.parseDouble(items.get(71)));
        residence.setParking((int) Double.parseDouble(items.get(74)));
        residence.setKitchen((int) Double.parseDouble(items.get(69)));
        residence.setHeating((int) Double.parseDouble(items.get(60)));
        residence.setId(items.get(0));
        residence.setPhotoPaths(new ArrayList<>());
        residence.setUsername(items.get(2));
        return residence;
    }

    private UserRegisterRequestDto createUser(List<String> items) {
        UserRegisterRequestDto user = new UserRegisterRequestDto();
        List<RoleDto> roleDtos = new ArrayList<>();
        roleDtos.add(RoleDto.TENANT);
        user.setEmail(items.get(4)+"@gmail.com");
        user.setName(items.get(4));
        user.setPhoneNumber("");
        user.setPassword("1234");
        user.setPhotoPath("");
        user.setRoleDtos(roleDtos);
        user.setUsername(items.get(2));
        return user;
    }

}
