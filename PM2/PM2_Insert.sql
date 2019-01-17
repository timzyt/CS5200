USE crimebuster;

INSERT INTO CrimeSubcategory (CrimeSubCategoryName) (
  SELECT Crime_Subcategory FROM Crime_Data
  GROUP BY Crime_Subcategory
  HAVING Crime_Subcategory != ''
  ORDER BY Crime_Subcategory);
  
INSERT INTO Sector (SectorName) (
  SELECT Sector FROM Crime_Data
  GROUP BY Sector
  HAVING Sector != ''
  ORDER BY Sector);
  
INSERT INTO Beat (BeatName) (
  SELECT Beat FROM Crime_Data
  GROUP BY Beat
  HAVING Beat != ''
  ORDER BY Beat);
  
INSERT INTO Neighborhood (NeighborhoodName) (
  SELECT Neighborhood FROM Crime_Data
  GROUP BY Neighborhood
  HAVING Neighborhood != ''
  ORDER BY Neighborhood);
  
INSERT INTO CrimeRecords (RecordId, OccurredTimeStamp, PrimaryOffenseDescription, Precinct)
  SELECT Report_Number, STR_TO_DATE(Occurred_Date, '%m/%d/%Y'), Primary_Offense_Description, Precinct
  FROM crime_Data;

UPDATE CrimeRecords
INNER JOIN
(SELECT CrimeSubCategoryId, Report_Number, Crime_Subcategory
  FROM crime_data
  INNER JOIN crimesubcategory
    ON crimesubcategoryName = Crime_Subcategory) AS C
ON crimerecords.RecordId = C.Report_Number
SET crimerecords.CrimeSubCategoryId = C.CrimeSubCategoryId;

UPDATE CrimeRecords
INNER JOIN
(SELECT SectorId, Report_Number, Sector
  FROM crime_data
  INNER JOIN Sector
    ON SectorName = Sector) AS C
ON crimerecords.RecordId = C.Report_Number
SET crimerecords.SectorId = C.SectorId;

UPDATE CrimeRecords
INNER JOIN
(SELECT BeatId, Report_Number, Beat
  FROM crime_data
  INNER JOIN beat
    ON beat.beatName = crime_data.Beat) AS C
ON crimerecords.RecordId = C.Report_Number
SET crimerecords.beatId = C.BeatId;

UPDATE CrimeRecords
INNER JOIN
(SELECT NeighborhoodId, Report_Number, Neighborhood
  FROM crime_data
  INNER JOIN Neighborhood
    ON NeighborhoodName = Neighborhood) AS C
ON crimerecords.RecordId = C.Report_Number
SET crimerecords.NeighborhoodId = C.NeighborhoodId;