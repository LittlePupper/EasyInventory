<?php
	require "conn.php";
	$productID = $_POST["productID"];
	$newStock = $_POST["newStock"];
	$stmt = "UPDATE Product SET Stock = $newStock WHERE ProductID = $productID;";
	if($conn->query($stmt) === FALSE) {
	    echo "Stock update failed";
	} else {
	    echo "Updated";
    }
?>