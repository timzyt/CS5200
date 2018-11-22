CREATE SCHEMA IF NOT EXISTS CrimeBuster;
USE CrimeBuster;

DROP TABLE IF EXISTS EditHistory;
DROP TABLE IF EXISTS Comments;
DROP TABLE IF EXISTS CrimeReports;
DROP TABLE IF EXISTS Users;
DROP TABLE IF EXISTS Admin;
DROP TABLE IF EXISTS Person;
DROP TABLE IF EXISTS CrimeCategory;
DROP TABLE IF EXISTS BeatSector;
DROP TABLE IF EXISTS SectorPrecinct;
DROP TABLE IF EXISTS Neighborhood;
DROP TABLE IF EXISTS Zipcode;

CREATE TABLE Zipcode(
	ZipcodeId INT AUTO_INCREMENT,
	Zipcode VARCHAR(25),
    CONSTRAINT pk_Zipcode_Zipcode PRIMARY KEY (ZipcodeId)
);

CREATE TABLE Neighborhood(
	NeighborhoodId INT AUTO_INCREMENT,
	NeighborhoodName VARCHAR(255),
    CONSTRAINT pk_NeighborhoodName_NeighborhoodName PRIMARY KEY (NeighborhoodId)
);

CREATE TABLE SectorPrecinct(
	SectorId INT AUTO_INCREMENT,
	Sector VARCHAR(255),
    Precinct ENUM('NORTH', 'WEST', 'EAST', 'SOUTH', 'SOUTHEAST', 'SOUTHWEST', 'UNKNOWN', ''),
    CONSTRAINT pk_SectorPrecinct_SectorId PRIMARY KEY(SectorId)
);

CREATE TABLE BeatSector(
	BeatId INT AUTO_INCREMENT,
	Beat VARCHAR(255),
    SectorId INT,
    CONSTRAINT pk_BeatSector_BeatId PRIMARY KEY(BeatId),
    CONSTRAINT fk_BeatSector_SectorId FOREIGN KEY(SectorId)
    REFERENCES SectorPrecinct(SectorId)
    ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE CrimeCategory(
	CrimeCategoryId	BIGINT AUTO_INCREMENT,
	PrimaryOffenseDescription VARCHAR(255),
    CrimeSubCategoryName VARCHAR(255),
    CONSTRAINT pk_CrimeCategory_CrimeCategoryId PRIMARY KEY(CrimeCategoryId)
);

CREATE TABLE Person(
	UserName VARCHAR(255),
    FirstName VARCHAR(255),
    LastName VARCHAR(255),
    PassWord VARCHAR(255),
    Email VARCHAR(255),
    Phone VARCHAR(255),
    CONSTRAINT pk_Person_UserName PRIMARY KEY (UserName)
);

Create Table Admin(
	UserName VARCHAR(255),
    isAdmin BOOLEAN DEFAULT TRUE,
    CONSTRAINT pk_Admin_UserName PRIMARY KEY (UserName),
    CONSTRAINT fk_Admin_UserName FOREIGN KEY (UserName)
    REFERENCES Person(UserName)
    ON UPDATE CASCADE ON DELETE CASCADE
);

Create Table Users(
	UserName VARCHAR(255),
    RegisteredAt DATETIME,
    CONSTRAINT pk_Users_UserName PRIMARY KEY (UserName),
    CONSTRAINT fk_Users_UserName FOREIGN KEY (UserName)
    REFERENCES Person(UserName)
    ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE CrimeReports(
	ReportId BIGINT AUTO_INCREMENT,
    UserName VARCHAR(255),
    OccurredTimeStamp DATETIME,
    ReportedTimeStamp DATETIME,
    InitialCallType BIGINT,
    FinalCallType BIGINT,
    BeatId INT,
    NeighborhoodId INT,
    ZipcodeId INT,
    CONSTRAINT pk_CrimeReports_ReportId PRIMARY KEY(ReportId),
    CONSTRAINT fk_CrimeReports_UserName FOREIGN KEY (UserName)
    REFERENCES Person(UserName)
    ON UPDATE CASCADE ON DELETE SET NULL,
    CONSTRAINT fk_CrimeReports_InitialCallType FOREIGN KEY(InitialCallType)
    REFERENCES CrimeCategory(CrimeCategoryId)
    ON UPDATE CASCADE ON DELETE SET NULL,
    CONSTRAINT fk_CrimeReports_FinalCallType FOREIGN KEY(FinalCallType)
    REFERENCES CrimeCategory(CrimeCategoryId)
    ON UPDATE CASCADE ON DELETE SET NULL,
    CONSTRAINT fk_CrimeReports_BeatId FOREIGN KEY (BeatId)
    REFERENCES BeatSector(BeatId)
    ON UPDATE CASCADE ON DELETE SET NULL,
    CONSTRAINT fk_CrimeReports_NeighborhoodId FOREIGN KEY (NeighborhoodId)
    REFERENCES Neighborhood(NeighborhoodId)
    ON UPDATE CASCADE ON DELETE SET NULL,
    CONSTRAINT fk_CrimeReports_ZipcodeId FOREIGN KEY (ZipcodeId)
    REFERENCES Zipcode(ZipcodeId)
    ON UPDATE CASCADE ON DELETE SET NULL
);

CREATE TABLE Comments(
	CommentId INT AUTO_INCREMENT,
    UserName VARCHAR(255),
    ReportId BIGINT,
    CommentContent VARCHAR(255),
    CommentTimeStamp TIMESTAMP,
    CONSTRAINT pk_Comments_CommentId PRIMARY KEY(CommentId),
    CONSTRAINT fk_Comments_UserName FOREIGN KEY (UserName)
    REFERENCES Person(UserName)
    ON UPDATE CASCADE ON DELETE CASCADE
);


CREATE TABLE EditHistory(
	EditHistoryId INT AUTO_INCREMENT,
    UserName VARCHAR(255),
    ReportId BIGINT,
    EditTime DATETIME,
    EditComment VARCHAR(255),
    CONSTRAINT pk_EditHistory_EditHistoryId PRIMARY KEY(EditHistoryId),
    CONSTRAINT fk_EditHistory_UserName FOREIGN KEY (UserName)
    REFERENCES Admin(UserName)
    ON UPDATE CASCADE ON DELETE SET NULL,
    CONSTRAINT fk_EditHistory_EditHistoryId FOREIGN KEY (ReportId)
    REFERENCES CrimeReports(ReportId)
    ON UPDATE CASCADE ON DELETE CASCADE
);









