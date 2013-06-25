CREATE TABLE  camion (
  ID Integer primary key  ,
  CAJONESMAXIMO int  NULL,
  CONDUCTOR VARCHAR NULL,
  NOMBRE VARCHAR NULL,
  TELEFONO VARCHAR NULL
);

CREATE TABLE cuadrilla (
  ID Integer primary key ,
  NOMBRE varchar  NULL,
  NUMEROCOLLIDORS int  NULL,
  TELEFONO varchar  NULL,
  ACTIVA boolean NULL
);

CREATE TABLE comprador (
  ID Integer primary key ,
  NOMBRE varchar DEFAULT NULL,
  TELEFONO varchar DEFAULT NULL

);





CREATE TABLE ordencollita (
  ID Integer primary key ,
  CAJONESPREVISTOS int  NULL,
  FECHACOLLITA date  NULL,
  CAMION_ID int  NULL,
  COMPRADOR_ID int  NULL,
  CUADRILLA_ID int  NULL,
  TERME_ID int  NULL,
  VARIEDAD_ID int  NULL,
  propietario varchar  NULL

);


CREATE TABLE  terme (
  ID Integer primary key ,
  NOMBRE varchar  NULL,
  PRECIOKILO double  NULL

);


CREATE TABLE  variedad (
  ID Integer primary key ,
  NOMBRE varchar  NULL,
  PRECIOKILO double  NULL
);