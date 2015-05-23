--liquibase formatted sql

--changeset josdem:1
LOCK TABLES `BANK_CODE` WRITE;
INSERT INTO `BANK_CODE` VALUES ('2001','BANXICO'),('37006','BANCOMEXT'),('37009','BANOBRAS'),('37019','BANJERCITO'),('37135','NAFIN'),('37166','BANSEFI'),('37168','HIPOTECARIA FED'),('40002','BANAMEX'),('40012','BBVA BANCOMER'),('40014','SANTANDER'),('40021','HSBC'),('40030','BAJIO'),('40032','IXE'),('40036','INBURSA'),('40037','INTERACCIONES'),('40042','MIFEL'),('40044','SCOTIABANK'),('40058','BANREGIO'),('40059','INVEX'),('40060','BANSI'),('40062','AFIRME'),('40072','BANORTE'),('40102','INVESTA BANK'),('40103','AMERICAN EXPRES'),('40106','BAMSA'),('40108','TOKYO'),('40110','JP MORGAN'),('40112','BMONEX'),('40113','VE POR MAS'),('40124','DEUTSCHE'),('40126','CREDIT SUISSE'),('40127','AZTECA'),('40128','AUTOFIN'),('40129','BARCLAYS'),('40130','COMPARTAMOS'),('40131','BANCO FAMSA'),('40132','BMULTIVA'),('40133','ACTINVER'),('40134','WAL-MART'),('40136','INTERBANCO'),('40137','BANCOPPEL'),('40138','ABC CAPITAL'),('40139','UBS BANK'),('40140','CONSUBANCO'),('40141','VOLKSWAGEN'),('40143','CIBANCO'),('40145','BBASE'),('40146','BICENTENARIO'),('40147','AGROFINANZAS'),('40148','PAGATODO'),('40150','INMOBILIARIO'),('40151','DONDE'),('40152','BANCREA'),('90600','MONEXCB'),('90601','GBM'),('90602','MASARI'),('90605','VALUE'),('90606','ESTRUCTURADORES'),('90607','TIBER'),('90608','VECTOR'),('90610','B&B'),('90614','ACCIVAL'),('90615','MERRILL LYNCH'),('90616','FINAMEX'),('90617','VALMEX'),('90618','UNICA'),('90619','MAPFRE'),('90620','PROFUTURO'),('90621','CB ACTINVER'),('90622','OACTIN'),('90623','SKANDIA'),('90626','CBDEUTSCHE'),('90627','ZURICH'),('90628','ZURICHVI'),('90630','CB INTERCAM'),('90631','CI BOLSA'),('90634','FINCOMUN'),('90636','HDI SEGUROS'),('90637','ORDER'),('90638','AKALA'),('90640','CB JPMORGAN'),('90642','REFORMA'),('90646','STP'),('90647','TELECOMM'),('90648','EVERCORE'),('90649','OSKNDIA'),('90651','SEGMTY'),('90652','ASEA'),('90653','KUSPIT'),('90655','SOFIEXPRESS'),('90656','UNAGRA'),('90659','ASP INTEGRA OPC'),('90670','LIBERTAD'),('90671','HUASTECAS'),('90673','GNP'),('90674','AXA'),('90675','FICREA'),('90677','CAJA POP MEXICA'),('90678','SURA'),('90901','CLS'),('90902','INDEVAL');
UNLOCK TABLES;

--changeset josdem:2
LOCK TABLES `PROJECT_CATEGORY` WRITE;
INSERT INTO `PROJECT_CATEGORY` VALUES (0,NULL,'Teatro'),(1,NULL,'Danza'),(2,NULL,'Musica'),(3,NULL,'Conciertos'),(4,NULL,'Espectaculos'),(5,NULL,'Opera'),(6,0,'Monologo'),(7,0,'Musical'),(8,0,'Cabaret'),(9,0,'Infantil'),(10,0,'Comedia'),(11,0,'Tragicomedia'),(12,0,'Contemporaneo'),(13,0,'Improvisacion'),(14,0,'Drama'),(15,0,'Melodrama'),(16,0,'Farsa'),(17,1,'Folklor'),(18,1,'Contemporaneo'),(19,1,'Clasico'),(20,1,'Valet'),(21,1,'Otros'),(22,2,'Recitales'),(23,2,'Clasica'),(24,2,'Contemporanea'),(25,2,'Otros'),(26,3,'Popular'),(27,3,'Rock'),(28,3,'Pop'),(29,3,'Jazz'),(30,3,'Otros'),(31,4,'Coreograficos'),(32,4,'Populares'),(33,4,'Performance'),(34,4,'Escenicos'),(35,4,'Infantiles'),(36,4,'Otros'),(37,5,'Zarzuela'),(38,5,'Clasica'),(39,5,'Contemporanea'),(40,5,'Opereta');
UNLOCK TABLES;

--changeset josdem:3
LOCK TABLES `TRAMA_ACCOUNT` WRITE;
INSERT INTO `TRAMA_ACCOUNT` VALUES (1,'0000000001','0.00','646180111900000001',0),(2,'0000000002','0.00','646180111900000014',1);
UNLOCK TABLES;

