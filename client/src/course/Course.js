import { React, useState } from 'react'
import CourseDetails from './CourseDetails';
import './index.css'

export default function Course({ course }) {
    const [clicked, setClicked] = useState(false)

    return (
        <div key={course.id} className="course-box flex-row-item">
            <h2 className="course-title">{course.name}</h2>
            <button type="button" className="open-course-button"
                onClick={() => setClicked(true)}
            >
                Open
            </button>
            {clicked &&
                <CourseDetails key={course.id} course={course} /> 
            }
        </div>
    );
}
