<?php
	require "conn.php";
	$productID = $_POST["productID"];
	$mysql_query = "SELECT * FROM Product WHERE ProductID = $productID;";
	$result = mysqli_query($conn, $mysql_query);
	if (mysqli_num_rows($result) > 0) {
		while($row = mysqli_fetch_array($result)) {
			echo $row['ProductID'] . '~' . $row['ProductName'] . '~' . $row['Price'] . '~' . $row['Size'] . '~' . $row['Unit'] . '~' . $row['Description'] . '~' . $row['Stock'];
		}			
	} else {
		echo "No products found";
	}
?>