import { useEffect, useState } from "react";
import "./App.css";
import Search from "./search/Search";

export default function App() {
  // eslint-disable-next-line
  const [courses, setCourses] = useState([]);
  const [loading, setLoading] = useState(false);

  // TODO should have refresh button
  // TODO should have welcome page without courses
  // TODO auth page to do firstly

  useEffect(() => {
    setLoading(true);

    // fetch("/courses")
    //   .then((response) => response.json())
    //   .then((data) => {
    //     setCourses(data);
    //     setLoading(false);
    //   });
    setCourses([]);
    setLoading(false);
  }, []);

  if (loading) {
    return <p>Loading...</p>;
  }

  return (
    <div>
      <header className="header-class"></header>
      <h1 className="title-name">Hello world!</h1>
      <Search courses={courses} />
      {courses.map((course) => {
        return (
          <div key={course.id}>
            <h2>name: {course.name}</h2>
            <h2>country: {course.description}</h2>
            <hr />
          </div>
        );
      })}
      <footer className="footer-class"></footer>
    </div>
  );
}
