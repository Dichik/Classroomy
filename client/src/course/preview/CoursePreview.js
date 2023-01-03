import { React } from 'react';
import { Link } from 'react-router-dom';
import './index.css';

export default function CoursePreview({ course }) {
    return (
        <div key={course.id} className="course-box">
            <h2 className="course-title">{course.name}</h2>
            <Link
                id="course.id"
                className="open-course-button"
                state={{ course: course }}
                to={`/courses/${course.id}`}
            >
                open
            </Link>
        </div>
    );
}
