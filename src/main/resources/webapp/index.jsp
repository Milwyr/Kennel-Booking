<html>
	<head><title>Kennel Booking</title></head>
	<body>
		<form action="index" method="post">
			<p>Hello ${username}</p>
			<div>
				<b>Vacancies in the kennel</b><br>
				${report}

			</div>
			<p><b>Dog size</b></p>
			<script>
				window.onload = function () {
					document.getElementById('dogsize').selectedIndex = -1;
				}
			</script>
			<select id="dogsize" name="dogsize">
				<option value="small">Small</option>
				<option value="medium">Medium</option>
				<option value="giant">Giant</option>
			</select>

			<p><b>Name of your dog</b></p>
			<input type="text" name="dogname">
			<p><input type="submit" name="book" value="Book"></p>
			<font color="green"> ${bookingconfirmation}</font>
			<p>Name of your dog to check out</p>
			<input type="text" name="checkoutdogname">
			<p><input type="submit" name="checkout" value="Check out"></p>
			<p><font color="blue">${checkoutconfirmation}</font></p>
		</form>
	</body>
</html>