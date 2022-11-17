import React, { useEffect, useState } from "react";
import "./App.css";

function App() {
  // eslint-disable-next-line
  const [courses, setCourses] = useState([]);
  const [loading, setLoading] = useState(false);

  // TODO should have refresh button
  // TODO should have welcome page without courses
  // TODO auth page to do firstly

  useEffect(() => {
    setLoading(true);

    fetch("api/courses")
      .then((response) => response.json())
      .then((data) => {
        setCourses(data);
        setLoading(false);
        console.log(courses);
      });
  }, []);

  if (loading) {
    return <p>Loading...</p>;
  }

  return (
    <div>
      <header className="header-class"></header>
      <h1 className="title-name">Hello world!</h1>${courses}
      <footer className="footer-class"></footer>
    </div>
  );
}

export default App;
