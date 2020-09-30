/* tslint:disable */
/* eslint-disable */
// Generated using typescript-generator version 2.25.695 on 2020-09-25 13:32:27.

export interface CommentDto {
    commentId: number;
    comment: string;
    grade: number;
    username: string;
}

export interface NewMessageDto {
    fromUser: string;
    toUser: string;
    messageText: string;
}

export interface AddCommentToResidenceDto {
    residenceId: number;
    comment: string;
    grade: number;
    username: string;
}

export interface AddResidenceRequestDto {
    id: string;
    address: string;
    geoX: number;
    geoY: number;
    datesReserved: string;
    capacity: number;
    prize: number;
    type: string;
    rules: string;
    description: string;
    bathrooms: number;
    size: number;
    bedrooms: number;
    livingRoom: boolean;
    location: string;
    wifi: number;
    kitchen: number;
    heating: number;
    elevator: number;
    parking: number;
    username: string;
    photoPaths: string[];
    title: string;
    beds: number;
}

export interface AddResidenceResponseDto {
    residenceId: number;
    address: string;
    geoX: number;
    geoY: number;
    datesReserved: string;
    capacity: number;
    prize: number;
    type: string;
    rules: string;
    description: string;
    bathrooms: number;
    size: number;
    bedrooms: number;
    beds: number;
    livingRoom: boolean;
    location: string;
    username: string;
    title: string;
    kitchen: number;
    heating: number;
    elevator: number;
    parking: number;
    wifi: number;
    photoPaths: string[];
    comments: CommentEntity[];
    reservationInfo: ReservationEntity[];
}

export interface ReservationDto {
    username: string;
    residenceId: number;
    arrivalDate: Date;
    departureDate: Date;
}

export interface SearchResidenceByIdDto {
    residenceId: number;
}

export interface SearchResidenceDto {
    username: string;
    location: string;
    arrivalDate: Date;
    departureDate: Date;
    capacity: number;
    wifi: number;
    kitchen: number;
    heating: number;
    parking: number;
    elevator: number;
}

export interface SessionInfo {
    username: string;
    date: Date;
}

export interface UserLogInRequestDto {
    username: string;
    password: string;
}

export interface UserLogInResponseDto {
    username: string;
    authToken: string;
    roleDtos: RoleDto[];
    photo: string;
}

export interface UserProfileDto {
    name: string;
    surname: string;
    email: string;
    phoneNumber: string;
    city: string;
    photo: string;
    comments: CommentEntity[];
    roleDtos: RoleDto[];
}

export interface UserRegisterRequestDto {
    username: string;
    password: string;
    name: string;
    surname: string;
    email: string;
    phoneNumber: string;
    photoPath: string;
    roleDtos: RoleDto[];
}

export interface UserRegisterResponseDto {
    username: string;
    password: string;
    name: string;
    surname: string;
    email: string;
    phoneNumber: string;
    city: string;
    photo: string;
    roleDtos: RoleDto[];
}

export interface UserUpdateProfileDto {
    username: string;
    name: string;
    surname: string;
    email: string;
    phoneNumber: string;
    city: string;
    photo: string;
    roleDtos: RoleDto[];
}

export interface UserUtilsDto {
    username: string;
}

export interface CommentEntity {
    commentId: number;
    comment: string;
    grade: number;
}

export interface ReservationEntity {
    arrivalDate: Date;
    departureDate: Date;
}

export type RoleDto = "HOST" | "TENANT";
