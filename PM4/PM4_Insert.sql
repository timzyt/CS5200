USE crimebuster;

INSERT INTO Zipcode (Zipcode) 
VALUES 
(98101),
(98102),
(98103),
(98104),
(98105),
(98106),
(98107),
(98108),
(98109),
(98110),
(98112),
(98115),
(98116),
(98117),
(98118),
(98119),
(98121),
(98122),
(98125),
(98126),
(98131),
(98133),
(98134),
(98136),
(98144),
(98146),
(98148),
(98154),
(98155),
(98158),
(98161),
(98164),
(98166),
(98168),
(98174),
(98177),
(98178),
(98188),
(98198),
(98199);

INSERT INTO Neighborhood (NeighborhoodName) (
  SELECT Neighborhood FROM crime_data_test
  GROUP BY Neighborhood
  HAVING Neighborhood != ''
  ORDER BY Neighborhood);

INSERT INTO SectorPrecinct (Sector, Precinct) (
  SELECT if(Sector='', 'NA', Sector), Precinct FROM crime_data_test
  GROUP BY Sector
  ORDER BY Sector);
  
INSERT INTO BeatSector (Beat) (
  SELECT if(Beat='','NA',Beat) AS Beat
  FROM crime_data_test
  GROUP BY Beat
  HAVING Beat != ''
  ORDER BY Beat);
  
UPDATE BeatSector
INNER JOIN
 (
SELECT Beat, SectorId
FROM
	(SELECT if(Beat='', 'NA', Beat) AS Beat, Sector 
	 FROM crime_data_test
	 GROUP BY Beat
	) AS B
	INNER JOIN SectorPrecinct
	ON B.Sector = SectorPrecinct.Sector
	) AS C
ON BeatSector.Beat = C.Beat
SET BeatSector.SectorId = C.SectorId;
  
INSERT INTO CrimeCategory (PrimaryOffenseDescription, CrimeSubCategoryName) (
  SELECT Primary_Offense_Description, Crime_SubCategory FROM crime_data_test
  GROUP BY Primary_Offense_Description
  HAVING Primary_Offense_Description != ''
  ORDER BY Primary_Offense_Description);
  
INSERT INTO Admin (UserName) 
VALUES 
('keyuc'),
('congl'),
('haoxiangm'),
('zeyiw'),
('yitianz');

INSERT INTO CrimeReports (ReportId, OccurredTimeStamp, ReportedTimeStamp, InitialCallType)
  SELECT Report_Number, STR_TO_DATE(Occurred_Date, '%m/%d/%Y'),
  STR_TO_DATE(Reported_Date, '%m/%d/%Y'), CrimeCategoryId
  FROM 
  crime_data_test LEFT JOIN crimecategory
  ON Crime_data_test.Primary_Offense_Description = crimecategory.PrimaryOffenseDescription;


INSERT INTO CrimeReports (ReportId, OccurredTimeStamp, ReportedTimeStamp, InitialCallType, BeatId, NeighborhoodId)
  SELECT Report_Number, STR_TO_DATE(Occurred_Date, '%m/%d/%Y'),
  STR_TO_DATE(Reported_Date, '%m/%d/%Y'), CrimeCategoryId, BeatId, NeighborhoodId
  FROM 
  crime_data_test LEFT JOIN crimecategory
  ON Crime_data_test.Primary_Offense_Description = crimecategory.PrimaryOffenseDescription
  LEFT JOIN BeatSector 
  ON Crime_data_test.Beat = BeatSector.Beat
  LEFT JOIN Neighborhood
  ON Crime_data_test.Neighborhood = Neighborhood.NeighborhoodName;
  
  SELECT Crime_data_test.Beat, BeatSector.BeatId, neighborhoodId
  FROM 
  Crime_data_test 
  LEFT JOIN BeatSector
  ON Crime_data_test.beat = BeatSector.beat
  LEFT JOIN Neighborhood
  ON Crime_data_test.neighborhood = Neighborhood.neighborhoodName;