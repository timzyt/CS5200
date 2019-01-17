CREATE SCHEMA IF NOT EXISTS CrimeBuster;
USE CrimeBuster;

DROP TABLE IF EXISTS CrimeReports;

DROP TABLE IF EXISTS CrimeRecords;
DROP TABLE IF EXISTS Comments;
DROP TABLE IF EXISTS CrimeSubCategory;
DROP TABLE IF EXISTS Person;
DROP TABLE IF EXISTS Neighborhood;
DROP TABLE IF EXISTS Sector;
DROP TABLE IF EXISTS Beat;
DROP TABLE IF EXISTS Locations;

CREATE TABLE Locations(
	LocationId INT AUTO_INCREMENT,
    BeatId INT,
    Zipcode VARCHAR(255),
    SectorId INT,
    NeighborhoodId INT,
    CONSTRAINT pk_Locations_LocationId PRIMARY KEY (LocationId)
);

CREATE TABLE Beat(
	BeatId INT AUTO_INCREMENT,
    BeatName VARCHAR(255),
    CONSTRAINT pk_Beat_BeatId PRIMARY KEY(BeatId)
);

CREATE TABLE Sector(
	SectorId INT AUTO_INCREMENT,
    SectorName VARCHAR(255),
    CONSTRAINT pk_Sector_SectorId PRIMARY KEY(SectorId)
);

CREATE TABLE Neighborhood(
	NeighborhoodId INT AUTO_INCREMENT,
    NeighborhoodName VARCHAR(255),
    CONSTRAINT pk_Neighborhood_NeighborhoodId PRIMARY KEY(NeighborhoodId)
);

CREATE TABLE CrimeSubCategory(
	CrimeSubCategoryId INT AUTO_INCREMENT,
    CrimeSubCategoryName VARCHAR(255),
    CONSTRAINT pk_CrimeSubCategory_CrimeSubCategoryId PRIMARY KEY(CrimeSubCategoryId)
);

CREATE TABLE Person(
	UserName VARCHAR(255),
    isAdmin BOOLEAN DEFAULT FALSE,
    FirstName VARCHAR(255),
    LastName VARCHAR(255),
    PassWord VARCHAR(255),
    Email VARCHAR(255),
    Phone VARCHAR(255),
    CONSTRAINT pk_Person_UserName PRIMARY KEY (UserName)
);

CREATE TABLE Comments(
	CommentId INT AUTO_INCREMENT,
    UserName VARCHAR(255),
    Zipcode VARCHAR(255),
    LocationId INT,
    CommentContent VARCHAR(255),
    CommentTimeStamp TIMESTAMP,
    CONSTRAINT pk_Comments_CommentId PRIMARY KEY(CommentId),
    CONSTRAINT fk_Comments_UserName FOREIGN KEY (UserName)
    REFERENCES Person(UserName)
    ON UPDATE CASCADE ON DELETE CASCADE
);


CREATE TABLE CrimeRecords(
	RecordId BIGINT AUTO_INCREMENT,
    LocationId INT,
    BeatId INT,
    OccurredTimeStamp DATETIME,
    CrimeSubCategoryId INT,
    PrimaryOffenseDescription VARCHAR(255),
    SectorId INT,
    NeighborhoodId INT,
    Precinct ENUM('NORTH', 'WEST', 'EAST', 'SOUTH', 'SOUTHEAST', 'SOUTHWEST', 'UNKNOWN', ''),
    CONSTRAINT pk_CrimeRecords_RecordId PRIMARY KEY(RecordId),
    CONSTRAINT fk_CrimeRecords_LocationId FOREIGN KEY (LocationId)
    REFERENCES Locations(LocationId)
    ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT fk_CrimeRecords_BeatId FOREIGN KEY (BeatId)
    REFERENCES Beat(BeatId)
    ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT fk_CrimeRecords_SectorId FOREIGN KEY (SectorId)
    REFERENCES Sector(SectorId)
    ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT fk_CrimeRecords_NeighborhoodId FOREIGN KEY (NeighborhoodId)
    REFERENCES Neighborhood(NeighborhoodId)
    ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT fk_CrimeRecords_CrimeSubCategoryId FOREIGN KEY(CrimeSubCategoryId)
    REFERENCES CrimeSubCategory(CrimeSubCategoryId)
);



CREATE TABLE CrimeReports(
	ReportId INT AUTO_INCREMENT,
    UserName VARCHAR(255) DEFAULT NULL,
    LocationId INT,
    OccurredTimeStamp DATETIME,
    ReportTimeStamp DATETIME,
    CrimeSubCategoryId INT,
    InitialCallType VARCHAR(255),
    FinalCallType VARCHAR(255),
    BeatId INT,
    SectorId INT,
    Precinct ENUM('NORTH', 'WEST', 'EAST', 'SOUTH', 'SOUTHEAST', 'SOUTHWEST', 'UNKNOWN', ''),
    CONSTRAINT pk_CrimeReports_ReportId PRIMARY KEY(ReportId),
    CONSTRAINT fk_CrimeReports_UserName FOREIGN KEY (UserName)
    REFERENCES Person(UserName)
    ON UPDATE CASCADE ON DELETE CASCADE
);







