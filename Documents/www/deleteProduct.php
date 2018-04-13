<?php
	require "conn.php";
	$productID = $_POST["productID"];
	$stmt = "DELETE FROM Product WHERE ProductID = $productID;";
	if($conn->query($stmt) === FALSE) {
	    echo "Delete failed";
	} else {
	    echo "Deleted";
    }
?>