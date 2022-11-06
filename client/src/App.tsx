import React, { useState } from "react";
import "./App.css";

function App() {
	// eslint-disable-next-line
	const [petitions, setPetitions] = useState([]);

	return (
		<div>
			<header className="header-class"></header>
			<h1 className="title-name">Hello world!</h1>
			<footer className="footer-class"></footer>
		</div>
	);
}

export default App;
