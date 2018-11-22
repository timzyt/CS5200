CREATE SCHEMA IF NOT EXISTS CrimeBuster;
USE CrimeBuster;

DROP TABLE IF EXISTS EditHistory;
DROP TABLE IF EXISTS Comments;
DROP TABLE IF EXISTS CrimeReports;
DROP TABLE IF EXISTS CrimeRecords;
DROP TABLE IF EXISTS Users;
DROP TABLE IF EXISTS Admin;
DROP TABLE IF EXISTS Person;
DROP TABLE IF EXISTS CrimeCategory;
DROP TABLE IF EXISTS BeatSector;
DROP TABLE IF EXISTS SectorPrecinct;
DROP TABLE IF EXISTS Neighborhood;
DROP TABLE IF EXISTS Zipcode;

CREATE TABLE Zipcode(
	Zipcode VARCHAR(25),
    CONSTRAINT pk_Zipcode_Zipcode PRIMARY KEY (Zipcode)
);

CREATE TABLE Neighborhood(
	NeighborhoodName VARCHAR(255),
    CONSTRAINT pk_NeighborhoodName_NeighborhoodName PRIMARY KEY (NeighborhoodName)
);

CREATE TABLE SectorPrecinct(
	Sector VARCHAR(255),
    Precinct ENUM('NORTH', 'WEST', 'EAST', 'SOUTH', 'SOUTHEAST', 'SOUTHWEST', 'UNKNOWN', ''),
    CONSTRAINT pk_SectorPrecinct_Sector PRIMARY KEY(Sector)
);

CREATE TABLE BeatSector(
	Beat VARCHAR(255),
    Sector VARCHAR(255),
    CONSTRAINT pk_BeatSector_Beat PRIMARY KEY(Beat),
    CONSTRAINT fk_BeatSector_Sector FOREIGN KEY(Sector)
    REFERENCES SectorPrecinct(Sector)
    ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE CrimeCategory(
	PrimaryOffenseDescription VARCHAR(255),
    CrimeSubCategoryName VARCHAR(255),
    CONSTRAINT pk_CrimeCategory_PrimaryOffenseDescription PRIMARY KEY(PrimaryOffenseDescription)
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

CREATE TABLE CrimeRecords(
	RecordId BIGINT AUTO_INCREMENT,
    UserName VARCHAR(255),
    OccurredTimeStamp DATETIME,
    ReportedTimeStamp DATETIME,
    InitialCallType VARCHAR(255),
    FinalCallType VARCHAR(255),
    Beat VARCHAR(255),
    NeighborhoodName VARCHAR(255),
    Zipcode VARCHAR(25),
    CONSTRAINT pk_CrimeRecords_RecordId PRIMARY KEY(RecordId),
    CONSTRAINT fk_CrimeRecords_UserName FOREIGN KEY (UserName)
    REFERENCES Person(UserName)
    ON UPDATE CASCADE ON DELETE SET NULL,
    CONSTRAINT fk_CrimeRecords_InitialCallType FOREIGN KEY(InitialCallType)
    REFERENCES CrimeCategory(PrimaryOffenseDescription)
    ON UPDATE CASCADE ON DELETE SET NULL,
    CONSTRAINT fk_CrimeRecords_FinalCallType FOREIGN KEY(FinalCallType)
    REFERENCES CrimeCategory(PrimaryOffenseDescription)
    ON UPDATE CASCADE ON DELETE SET NULL,
    CONSTRAINT fk_CrimeRecords_Beat FOREIGN KEY (Beat)
    REFERENCES BeatSector(Beat)
    ON UPDATE CASCADE ON DELETE SET NULL,
    CONSTRAINT fk_CrimeRecords_NeighborhoodName FOREIGN KEY (NeighborhoodName)
    REFERENCES Neighborhood(NeighborhoodName)
    ON UPDATE CASCADE ON DELETE SET NULL,
    CONSTRAINT fk_CrimeRecords_Zipcode FOREIGN KEY (Zipcode)
    REFERENCES Zipcode(Zipcode)
    ON UPDATE CASCADE ON DELETE SET NULL
);

CREATE TABLE CrimeReports(
	ReportId BIGINT AUTO_INCREMENT,
    UserName VARCHAR(255),
    OccurredTimeStamp DATETIME,
    ReportedTimeStamp DATETIME,
    InitialCallType VARCHAR(255),
    FinalCallType VARCHAR(255),
    Beat VARCHAR(255),
    NeighborhoodName VARCHAR(255),
    Zipcode VARCHAR(25),
    CONSTRAINT pk_CrimeReports_ReportId PRIMARY KEY(ReportId),
    CONSTRAINT fk_CrimeReports_UserName FOREIGN KEY (UserName)
    REFERENCES Person(UserName)
    ON UPDATE CASCADE ON DELETE SET NULL,
    CONSTRAINT fk_CrimeReports_InitialCallType FOREIGN KEY(InitialCallType)
    REFERENCES CrimeCategory(PrimaryOffenseDescription)
    ON UPDATE CASCADE ON DELETE SET NULL,
    CONSTRAINT fk_CrimeReports_FinalCallType FOREIGN KEY(FinalCallType)
    REFERENCES CrimeCategory(PrimaryOffenseDescription)
    ON UPDATE CASCADE ON DELETE SET NULL,
    CONSTRAINT fk_CrimeReports_Beat FOREIGN KEY (Beat)
    REFERENCES BeatSector(Beat)
    ON UPDATE CASCADE ON DELETE SET NULL,
    CONSTRAINT fk_CrimeReports_NeighborhoodName FOREIGN KEY (NeighborhoodName)
    REFERENCES Neighborhood(NeighborhoodName)
    ON UPDATE CASCADE ON DELETE SET NULL,
    CONSTRAINT fk_CrimeReports_Zipcode FOREIGN KEY (Zipcode)
    REFERENCES Zipcode(Zipcode)
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
    RecordId BIGINT,
    EditTime DATETIME,
    EditComment VARCHAR(255),
    CONSTRAINT pk_EditHistory_EditHistoryId PRIMARY KEY(EditHistoryId),
    CONSTRAINT fk_EditHistory_UserName FOREIGN KEY (UserName)
    REFERENCES Admin(UserName)
    ON UPDATE CASCADE ON DELETE SET NULL,
    CONSTRAINT fk_EditHistory_EditHistoryId FOREIGN KEY (RecordId)
    REFERENCES CrimeRecords(RecordId)
    ON UPDATE CASCADE ON DELETE CASCADE
);









