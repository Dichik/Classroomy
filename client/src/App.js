import { React } from 'react'
import { useEffect, useState } from 'react';
import './index.css';
import Course from './course/Course';
import Search from './search/Search';

export default function App() {
    const [input, setInput] = useState('');
    const [courses, setCourses] = useState([]);
    const [loading, setLoading] = useState(false);
    const initCoursesSet = [
        {
            id: 1,
            name: 'Algorithms',
            description: 'Course description'
        },
        {
            id: 2,
            name: 'Ukrainian language',
            description: 'Course description'
        },
        {
            id: 3,
            name: 'Algorithms',
            description: 'Course description'
        },
        {
            id: 4,
            name: 'Math',
            description: 'Course description'
        }
    ]

    // TODO should have refresh button
    // TODO should have welcome page without courses
    // TODO auth page to do firstly

    const loadCourses = () => {
        // fetch('/courses')
        //     .then((response) => response.json())
        //     .then((data) => {
        //         setCourses(data);
        //         setLoading(false);
        //     });
        setCourses(initCoursesSet)
    };

    useEffect(() => {
        setLoading(true);

        setCourses(initCoursesSet);
        setLoading(false);
    }, []);

    if (loading) {
        return <p>Loading...</p>;
    }

    // eslint-disable-next-line
    const filterCourses = () => {
        console.log(input)
        if (input === '') {
            loadCourses();
            return;
        }
        setCourses(courses.filter(course => course.name.includes(input)))
    };

    return (
        <div>
            <header className="header-class"></header>
            <h1 className="title-name">Hello world!</h1>
            <Search input={input} setInput={setInput} filterCourses={filterCourses} />
            <div className="flex-row-container">
                {courses.map((course) => {
                    return <Course key={course.id} course={course} />;
                })}
            </div>
            <footer className="footer-class"></footer>
        </div>
    );
}
