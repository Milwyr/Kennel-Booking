<html>
	<head><title>Kennel Booking</title></head>
	<body>
		<p>Hello ${username}</p>
		<div>
			<b>Vacancies in the kennel</b><br>
			${report_line1}<br>
			${report_line2}<br>
			${report_line3}<br>
			${report_line4}<br>
			${report_line5}<br>
			${report_line6}<br>
			${report_line7}<br>
			${report_line8}<br>
		</div>
		<p><b>Dog size</b></p>
		<select name="dogsize">
			<option value="small">Small</option>
			<option value="medium">Medium</option>
			<option value="giant">Giant</option>
		</select>
		<p><b>Name of your dog</b></p>
		<input type="text" name="dogname">
		<p><input type="submit" name="book" value="Book"></p>
	</body>
</html>