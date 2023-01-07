import React, { Component } from 'react';
import { Link } from 'react-router-dom';
// import './index.css';

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
            <div key={course.id}>
                <h2>{course.name}</h2>
                <Link
                    id={`${course.id}`}
                    to={{
                        pathname: `/courses/${course.id}`
                    }}
                    state={{ course }}
                >
                    open
                </Link>
                <Link
                    id={`${course.id}`}
                    to={{
                        pathname: `/students/${course.id}`
                    }}
                >
                    open
                </Link>
            </div>
        );
    }
}
