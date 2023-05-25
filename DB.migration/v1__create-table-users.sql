create table usuario (

    id bigint not null auto_increment,
    nombre varchar(30) not null,
    apellido varchar(30) not null,
    login varchar(100) not null,
    clave varchar(300) not null unique,


    primary key(id)


)