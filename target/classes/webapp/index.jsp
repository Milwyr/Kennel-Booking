<html>
	<head><title>Kennel Booking</title></head>
	<body>
		<form action="index" method="post">
			<p>Hello ${username}</p>
			<p>Current time is <span id="currentTime"></span></p>
			<div>
				<b>Vacancies in the kennel</b><br>
				${report}

			</div>
			<div>
				<p><b>Dog size</b></p>
				<script src="index.js"></script>
				<select id="dogsize" name="dogsize">
					<option value="small">Small</option>
					<option value="medium">Medium</option>
					<option value="giant">Giant</option>
				</select>
			</div>
			<div>
				<p><b>Name of your dog</b></p>
				<input type="text" name="dogname">
				<p><input type="submit" name="book" value="Book"></p>
				<font color="green"> ${bookingconfirmation}</font>
			</div>
			<div>
				<p>Name of your dog to check out</p>
				<input type="text" name="checkoutdogname">
				<p><input type="submit" name="checkout" value="Check out"></p>
				<p><font color="blue">${checkoutconfirmation}</font></p>
			</div>
		</form>
	</body>
</html>