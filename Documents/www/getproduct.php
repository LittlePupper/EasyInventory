<?php
	require "conn.php";
	$productID = $_POST["productID"];
	$mysql_query = "SELECT * FROM Product WHERE ProductID = $productID;";
	$result = mysqli_query($conn, $mysql_query);
	if (mysqli_num_rows($result) > 0) {
		while($row = mysqli_fetch_array($result)) {
			echo $row['ProductName'];
		}			
	} else {
		echo "Product search failure";
	}
?>