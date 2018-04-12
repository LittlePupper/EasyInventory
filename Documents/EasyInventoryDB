DROP DATABASE if exists EasyInventoryDB;
CREATE DATABASE EasyInventoryDB;

USE EasyInventoryDB;

CREATE TABLE Product (
	ProductID		INT(6) 			NOT NULL,
	ProductName		VARCHAR(50) 	NOT NULL,
	Price			FLOAT(5,2)		NOT NULL,
	Size			INT(4)			NOT NULL,
	Unit			VARCHAR(2)		NOT NULL,
	Description		VARCHAR(400)	NOT NULL,
	Stock			INT(5)			NOT NULL,
	PRIMARY KEY	(ProductID)
);

CREATE TABLE Image (
	ImageID			INT(6)			AUTO_INCREMENT,
	ProductID		INT(6)			NOT NULL,
	Path			VARCHAR(200)	NOT NULL,
	PRIMARY KEY	(ImageID),
	FOREIGN KEY	(ProductID)	REFERENCES Product(ProductID)
);

INSERT INTO Product (ProductID, ProductName, Price, Size, Unit, Description, Stock)
	 VALUES (1, 'Maple Walnut Tub', 25.00, 950, 'mL', 'Canadian classic sweetened with real Canadian maple syrup and loaded with crunchy toasted walnuts.', 20);

INSERT INTO Product (ProductID, ProductName, Price, Size, Unit, Description, Stock)
	 VALUES (2, 'Tiger Tub', 25.00, 950, 'mL', 'Orange flavoured ice cream with black licorice swirl.', 18);

INSERT INTO Product (ProductID, ProductName, Price, Size, Unit, Description, Stock)
	 VALUES (3, 'Vanilla Tub', 25.00, 950, 'mL', 'Classic creamy vanilla ice cream.', 40);

INSERT INTO Product (ProductID, ProductName, Price, Size, Unit, Description, Stock)
	 VALUES (4, 'Mint Chocolate Chip Tub', 25.00, 950, 'mL', 'Mint flavoured ice cream with lots of chocolate chips throughout.', 30);