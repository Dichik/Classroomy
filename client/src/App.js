import { useEffect, useState } from "react";
import "./App.css";
import Course from "./course/Course";
import Search from "./search/Search";

export default function App() {
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
    setCourses([
      {
        'id': 1,
        'name': 'Algorithms',
        'description': 'Course description'
      },
      {
        'id': 2,
        'name': 'Algorithms',
        'description': 'Course description'
      },
      {
        'id': 3,
        'name': 'Algorithms',
        'description': 'Course description'
      }
    ]);
    setLoading(false);
  }, []);

  if (loading) {
    return <p>Loading...</p>;
  }

  return (
    <div>
      <header className="header-class"></header>
      <h1 className="title-name">Hello world!</h1>
      <Search />
      <div className="flex-row-container">
        {courses.map((course) => {
          return <Course course={course} />;
        })}
      </div>
      <footer className="footer-class"></footer>
    </div>
  );
}
