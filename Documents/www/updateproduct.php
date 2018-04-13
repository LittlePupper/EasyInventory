<?php
	require "conn.php";
	$productID = $_POST["productID"];
	$productName = $_POST["productName"];
	$price = $_POST["price"];
	$size = $_POST["size"];
	$unit = $_POST["unit"];
	$description = $_POST["description"];
	$stmt = "UPDATE Product SET ProductName = '" . $productName . "', Price = $price, Size = $size, Unit = '" . $unit . "', Description = '" . $description . "' WHERE ProductID = $productID;";
	if($conn->query($stmt) === FALSE) {
	    echo "Product update failed";
	} else {
	    echo "Product updated";
    }
?>