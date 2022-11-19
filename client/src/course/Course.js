import { React } from 'react';
import { useNavigate } from 'react-router-dom';
import './index.css';

const Course = ({ course }) => {
    let navigate = useNavigate();

    return (
        <div key={course.id} className="course-box flex-row-item">
            <h2 className="course-title">{course.name}</h2>
            <button
                type="button"
                className="open-course-button"
                onClick={() => navigate(`/courses/${course.id}`)}
            >
                Open
            </button>
            {/* <CourseDetails key={course.id} course={course} />  */}
        </div>
    );
};

export default Course;
