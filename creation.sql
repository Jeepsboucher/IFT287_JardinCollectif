CREATE TABLE Member (
	memberId INT NOT NULL PRIMARY KEY,
	firstName VARCHAR(50) NOT NULL,
	lastName VARCHAR(50) NOT NULL,
	password VARCHAR(50) NOT NULL,
	isAdmin BOOLEAN DEFAULT FALSE NOT NULL
);

CREATE TABLE Plant (
    plantName VARCHAR(50) NOT NULL PRIMARY KEY,
    cultivationTime INT NOT NULL
);

CREATE TABLE Lot (
    lotName VARCHAR(50) NOT NULL PRIMARY KEY,
    maxMemberCount INT NOT NULL
);

CREATE TABLE RequestToJoin (
    requestStatus BOOLEAN DEFAULT FALSE NOT NULL,
    memberId INT NOT NULL REFERENCES member(memberId),
    lotName VARCHAR(50) NOT NULL REFERENCES lot(lotName),
    PRIMARY KEY (memberId, lotName)
);

CREATE TABLE IsSowedIn (
    isSowedInId SERIAL PRIMARY KEY,
    quantity INT NOT NULL,
    plantingDate DATE NOT NULL,
    memberId INT NOT NULL REFERENCES member(memberId),
    lotName VARCHAR(50) NOT NULL REFERENCES lot(lotName),
    plantName VARCHAR(50) NOT NULL REFERENCES plant(plantName)
);