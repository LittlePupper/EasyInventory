<?php
	require "conn.php";
	$productID = $_POST["productID"];
	$productName = $_POST["productName"];
	$price = $_POST["price"];
	$size = $_POST["size"];
	$unit = $_POST["unit"];
	$description = $_POST["description"];
	$stmt = "INSERT INTO Product (ProductID, ProductName, Price, Size, Unit, Description, Stock) VALUES ($productID, '" . $productName . "', $price, $size, '" . $unit . "', '" . $description . "', 0);";
	if($conn->query($stmt) === FALSE) {
	    echo "Product insert failed";
	} else {
	    echo "Product inserted";
    }
?>