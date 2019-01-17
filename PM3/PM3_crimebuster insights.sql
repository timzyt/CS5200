USE crimebuster;

# 1. How many total crimes were reported each year happened for the past decade?
SELECT COUNT(*) AS TotalAnnulCrime, Year(crimerecords.OccurredTimeStamp) AS OccurredYear
FROM crimerecords
GROUP BY OccurredYear
ORDER BY OccurredYear DESC
LIMIT 10;

# 2. What is the most reported crime subcategory in each year for the past decade?
SELECT CrimeSubCategoryName, MaxCrimeTypeCount, OccurredYear
FROM (
	SELECT c1.CrimeSubCategoryId, MaxCrimeTypeCount, c1.OccurredYear
	FROM (
		SELECT crimerecords.CrimeSubCategoryId, COUNT(IF(CrimeSubCategoryId IS NULL, 1, 1)) AS CrimeTypeCount, Year(OccurredTimeStamp) AS OccurredYear
		FROM crimerecords
		GROUP BY OccurredYear, CrimeSubCategoryId
		HAVING OccurredYear >= 2008
		ORDER BY CrimeTypeCount DESC
		) AS c1
	INNER JOIN (
		SELECT MAX(tmp.CrimeTypeCount) AS MaxCrimeTypeCount, OccurredYear
		FROM (
			SELECT crimerecords.CrimeSubCategoryId, COUNT(IF(CrimeSubCategoryId IS NULL, 1, 1)) AS CrimeTypeCount, Year(OccurredTimeStamp) AS OccurredYear
			FROM crimerecords
			GROUP BY OccurredYear, CrimeSubCategoryId
			HAVING OccurredYear >= 2008
			ORDER BY CrimeTypeCount DESC) AS tmp
		GROUP BY OccurredYear
		ORDER BY OccurredYear DESC
		LIMIT 10) AS c2
	ON c1.CrimeTypeCount = c2.MaxCrimeTypeCount AND c1.OccurredYear = c2.OccurredYear) AS c
INNER JOIN CrimeSubCategory
ON c.CrimeSubCategoryId = CrimeSubCategory.CrimeSubCategoryId;

# 3. What are the top ten reported crime subcategories since 2015-01-01?
SELECT CrimeSubCategoryName
FROM (

SELECT CrimeSubCategoryId, COUNT(crimerecords.RecordId) AS TotalAnnualCrime, Year(OccurredTimeStamp) AS OccurredYear
FROM crimerecords
GROUP BY CrimeSubCategoryId 
HAVING OccurredYear >= '2015'
ORDER BY TotalAnnualCrime DESC
LIMIT 10
) AS c 
LEFT JOIN crimesubcategory
ON c.crimeSubCategoryId = crimesubcategory.CrimeSubCategoryId;

# 4. What are the ten least-safe neighborhoods (with most reported crimes) for the year 2018 (since 2018-01-01)?
SELECT NeighborhoodName, TotalAnnualCrime
FROM
(
SELECT NeighborhoodId, COUNT(*) AS TotalAnnualCrime
FROM crimerecords
WHERE OccurredTimeStamp >= '2018-01-01'
GROUP BY NeighborhoodId
ORDER BY TotalAnnualCrime DESC
LIMIT 10
) as n
LEFT JOIN neighborhood
ON n.NeighborhoodId = neighborhood.NeighborhoodId;

#5. For the five least-safe neighborhoods for the year 2018, what are the top 3 most reported crime subcategory for each of them?
SELECT NeighborhoodName, TotalAnnualCrime, CrimeSubCategoryName, CNT AS NumOfCrimeSubCategory
FROM (
	SELECT CrimeRecords.NeighborhoodId, NeighborhoodName, COUNT(*) AS TotalAnnualCrime
	FROM CrimeRecords
    INNER JOIN Neighborhood
		ON CrimeRecords.NeighborhoodId = Neighborhood.NeighborhoodId
	WHERE OccurredTimeStamp >= '2018-01-01'
	GROUP BY NeighborhoodId
	ORDER BY TotalAnnualCrime DESC
	LIMIT 5) AS Top5Neighborhood
INNER JOIN (
	SELECT NeighborhoodId, CrimeSubCategoryName, COUNT(*) AS CNT
	FROM CrimeRecords
	INNER JOIN CrimeSubCategory
		ON CrimeRecords.CrimeSubCategoryId = CrimeSubCategory.CrimeSubCategoryId
	GROUP BY NeighborhoodId, CrimeRecords.CrimeSubCategoryId) AS CntPerNeighborPerCrime1
ON Top5Neighborhood.NeighborhoodId = CntPerNeighborPerCrime1.NeighborhoodId
WHERE
    3 > (
		SELECT COUNT(CntPerNeighborPerCrime2.CNT)
		FROM (
			SELECT NeighborhoodId, CrimeSubCategoryName, COUNT(*) AS CNT
			FROM CrimeRecords
			INNER JOIN CrimeSubCategory
				ON CrimeRecords.CrimeSubCategoryId = CrimeSubCategory.CrimeSubCategoryId
			GROUP BY NeighborhoodId, CrimeRecords.CrimeSubCategoryId) AS CntPerNeighborPerCrime2
		WHERE
			CntPerNeighborPerCrime2.CNT > CntPerNeighborPerCrime1.CNT 
				AND CntPerNeighborPerCrime1.NeighborhoodId = CntPerNeighborPerCrime2.NeighborhoodId)
ORDER BY TotalAnnualCrime DESC, NeighborhoodName, NumOfCrimeSubCategory DESC;

#6. What are the ten safest neighborhoods (with least reported crimes) for the year 2018 (since 2018-01-01)?
SELECT 
	NeighborhoodName, COUNT(*) AS Report_CNT
FROM
	(SELECT crimerecords.NeighborhoodId, NeighborhoodName, RecordId, OccurredTimeStamp
    FROM CrimeRecords
    LEFT JOIN Neighborhood
    ON CrimeRecords.NeighborhoodId = Neighborhood.NeighborhoodId
    WHERE OccurredTimeStamp >= '2018-01-01') AS T
GROUP BY NeighborhoodName
ORDER BY Report_CNT ASC
LIMIT 10;

#7. For the five safest neighborhoods for the year 2018, what are the top 3 most reported crime subcategory for each of them?
SELECT NeighborhoodId, Count(*)
FROM CrimeBuster.CrimeRecords
Group By NeighborhoodId
Order By Count(*) Asc
Limit 5;

(Select NeighborhoodId, CrimeSubCategoryId, Count(*) As NumOfRecords
From CrimeBuster.CrimeRecords
Where NeighborhoodId = 14
Group By CrimeSubCategoryId
Order By Count(*) Desc
Limit 3) 
Union all
(Select NeighborhoodId, CrimeSubCategoryId, Count(*) As NumOfRecords
From CrimeBuster.CrimeRecords
Where NeighborhoodId = 13
Group By CrimeSubCategoryId
Order By Count(*) Desc
Limit 3) 
Union all
(Select NeighborhoodId, CrimeSubCategoryId, Count(*) As NumOfRecords
From CrimeBuster.CrimeRecords
Where NeighborhoodId =  44
Group By CrimeSubCategoryId
Order By Count(*) Desc
Limit 3) 
Union all
(Select NeighborhoodId, CrimeSubCategoryId, Count(*) As NumOfRecords
From CrimeBuster.CrimeRecords
Where NeighborhoodId =  16
Group By CrimeSubCategoryId
Order By Count(*) Desc
Limit 3)
Union all
(Select NeighborhoodId, CrimeSubCategoryId, Count(*) As NumOfRecords
From CrimeBuster.CrimeRecords
Where NeighborhoodId =  21
Group By CrimeSubCategoryId
Order By Count(*) Desc
Limit 3);

#8. Comparing year 2015 against 2017, what are the top 10 crime subcategories with the most declining number of reports?
SELECT YEAR_2017.CrimeSubCategoryName, (YEAR_2015.CNT - YEAR_2017.CNT) AS NumOfDecline
FROM (
	SELECT CrimeRecords.CrimeSubCategoryId, CrimeSubCategoryName, COUNT(*) AS CNT
	FROM CrimeRecords
    INNER JOIN CrimeSubCategory
		ON CrimeRecords.CrimeSubCategoryId = CrimeSubCategory.CrimeSubCategoryId
	WHERE YEAR(OccurredTimeStamp) = 2015
	GROUP BY CrimeRecords.CrimeSubCategoryId, CrimeSubCategoryName) AS YEAR_2015
INNER JOIN (
	SELECT CrimeRecords.CrimeSubCategoryId, CrimeSubCategoryName, COUNT(*) AS CNT
	FROM CrimeRecords
    INNER JOIN CrimeSubCategory
		ON CrimeRecords.CrimeSubCategoryId = CrimeSubCategory.CrimeSubCategoryId
	WHERE YEAR(OccurredTimeStamp) = 2017
	GROUP BY CrimeRecords.CrimeSubCategoryId, CrimeSubCategoryName) AS YEAR_2017
ON YEAR_2015.CrimeSubCategoryId = YEAR_2017.CrimeSubCategoryId
ORDER BY NumOfDecline DESC
LIMIT 10;

#9. Comparing year 2015 against 2017, what are the top 10 crime subcategories with the most growing number of reports?
SELECT YEAR_2017.CrimeSubCategoryName, (YEAR_2017.CNT - YEAR_2015.CNT) AS NumOfGrowth
FROM (
 SELECT CrimeRecords.CrimeSubCategoryId, CrimeSubCategoryName, COUNT(*) AS CNT
 FROM CrimeRecords
    INNER JOIN CrimeSubCategory
  ON CrimeRecords.CrimeSubCategoryId = CrimeSubCategory.CrimeSubCategoryId
 WHERE YEAR(OccurredTimeStamp) = 2015
 GROUP BY CrimeRecords.CrimeSubCategoryId, CrimeSubCategoryName) AS YEAR_2015
INNER JOIN (
 SELECT CrimeRecords.CrimeSubCategoryId, CrimeSubCategoryName, COUNT(*) AS CNT
 FROM CrimeRecords
    INNER JOIN CrimeSubCategory
  ON CrimeRecords.CrimeSubCategoryId = CrimeSubCategory.CrimeSubCategoryId
 WHERE YEAR(OccurredTimeStamp) = 2017
 GROUP BY CrimeRecords.CrimeSubCategoryId, CrimeSubCategoryName) AS YEAR_2017
ON YEAR_2015.CrimeSubCategoryId = YEAR_2017.CrimeSubCategoryId
ORDER BY NumOfGrowth DESC
LIMIT 10;

#10. Comparing year 2015 against 2017, what are the top five neighborhoods that are becoming less safe? 
#(with the most increase in number of crime reports)
SELECT NeighborhoodId, Count(*)
FROM CrimeBuster.CrimeRecords
Group By NeighborhoodId
Order By Count(*) Asc
Limit 5;

SELECT neighborhood.NeighborhoodName, T.Difference
FROM
(
Select Rec17.NeighborhoodId, (Rec17.Rec2017 - Rec15.Rec2015) As Difference
From
(Select NeighborhoodId, Count(*) As Rec2017
From CrimeBuster.CrimeRecords
Where Year(OccurredTimeStamp) = 2017
Group by NeighborhoodId
Order By NeighborhoodId Asc) As Rec17
Inner Join
(Select NeighborhoodId, Count(*) As Rec2015
From CrimeBuster.CrimeRecords
Where Year(OccurredTimeStamp) = 2015
Group by NeighborhoodId
Order By NeighborhoodId Asc) As Rec15
On Rec17.NeighborhoodId = Rec15.NeighborhoodId
Order By Difference Desc
Limit 5) AS T
LEFT JOIN neighborhood
ON neighborhood.neighborhoodId = T.neighborhoodId;
