<?php
	require "conn.php";
	// $productID = $_POST["productID"];
	// $newStock = $_POST["newStock"]
	$productID = 3;
	$newStock = 40;
	$stmt = "UPDATE Product SET Stock = $newStock WHERE ProductID = $productID;";
	$stmt->execute();
	if($stmt->affected_rows === -1) {
	    echo 'Stock update failed';
	} else {
	    echo 'Stock updated';
    }
	$stmt->close();
?>