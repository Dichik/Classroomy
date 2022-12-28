import { React } from 'react';
import { useNavigate } from 'react-router-dom';
import './index.css';

const CoursePreview = ({ course }) => {
    let navigate = useNavigate();

    return (
        <div key={course.id} className="course-box flex-row-item">
            <h2 className="course-title">{course.name}</h2>
            <button
                type="button"
                className="open-course-button"
                onClick={() => navigate(`/courses/${course.id}`)}
            >
                open
            </button>
        </div>
    );
};

export default CoursePreview;
