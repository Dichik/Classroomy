import React, { Component } from 'react';
import { Link } from 'react-router-dom';
import './index.css';

export default class CoursePreview extends Component {
    constructor(props) {
        super(props);

        this.state = {
            course: this.props.course
        };
    }

    render() {
        const course = this.state.course;

        return (
            <div className="course-preview-box" key={course.id}>
                <h2 className="course-title">{course.name}</h2>
                <div className="buttons">
                    <Link
                        id={`${course.id}`}
                        className="open-course-button"
                        to={{
                            pathname: `/courses/${course.id}`
                        }}
                        state={{ course }}
                    >
                        open
                    </Link>
                    <Link
                        id={`${course.id}`}
                        className="open-course-button"
                        to={{
                            pathname: `/students/${course.id}`
                        }}
                    >
                        students
                    </Link>
                </div>
            </div>
        );
    }
}
