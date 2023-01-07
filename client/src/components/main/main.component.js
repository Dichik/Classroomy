import React, { Component } from 'react';
import { Link } from 'react-router-dom';
import CoursePreview from '../course/course.preview.component';
import Spinner from '../spinner/spinner.component';
import courseService from '../../services/course.service';
import authService from '../../services/auth.service';
import CheckButton from 'react-validation/build/button';
import Form from 'react-validation/build/form';
import { Input } from 'reactstrap';
import './index.css';

const filter = (courses, input) => {
    const result = [];
    courses.forEach((course) => {
        if (course.name === input) {
            result.push(course);
        }
    });
    return result;
};

export default class Main extends Component {
    constructor(props) {
        super(props);

        this.onChangeInput = this.onChangeInput.bind(this);
        this.onChangeCourses = this.onChangeCourses.bind(this);
        this.onChangeLoading = this.onChangeLoading.bind(this);
        this.onChangeEnrollmentKey = this.onChangeEnrollmentKey.bind(this);

        this.state = {
            input: '',
            courses: [],
            loading: false,
            filteredCourses: [],
            user: undefined,
            showEnrollmentField: false,
            enrollmentKey: '',
            message: ''
        };
    }

    onChangeInput(e) {
        this.setState({
            input: e.target.value
        });
    }

    onChangeCourses(courses) {
        this.setState({
            courses: courses,
            filteredCourses: courses
        });
    }

    onChangeLoading() {
        this.setState({
            loading: !this.state.loading
        });
    }

    onChangeEnrollmentKey(e) {
        this.setState({
            enrollmentKey: e.target.value
        });
    }

    componentDidMount() {
        const user = authService.getCurrentUser();

        if (user) {
            this.setState({
                currentUser: user,
                showEnrollmentField: user.roles.includes('ROLE_STUDENT')
            });
        }

        this.onChangeLoading;
        courseService
            .getCourses()
            .then((response) => response.data)
            .then((data) => {
                console.log(data);
                this.onChangeCourses(data);
                this.onChangeLoading;
            });
    }

    enrollToCourse = () => {
        courseService
            .enrollToCourse(this.state.enrollmentKey)
            .then((response) => response.data)
            .then((data) => {
                console.log(data);
                this.setState({
                    successful: true,
                    message: data
                });
            });
    };

    filterCourses = (e) => {
        e.preventDefault();

        if (this.state.input === '') {
            this.setState({
                filteredCourses: this.state.courses
            });
            return;
        }
        this.setState({
            filteredCourses: filter(this.state.courses, this.state.input)
        });
    };

    render() {
        return (
            <div>
                {this.state.loading ? (
                    <Spinner />
                ) : (
                    <div>
                        {this.state.showEnrollmentField ? (
                            <div>
                                <Form
                                    className="search-input"
                                    onSubmit={this.enrollToCourse}
                                >
                                    {!this.state.successful && (
                                        <div>
                                            <div className="form-group search-input-field">
                                                <Input
                                                    type="text"
                                                    className="form-control"
                                                    name="key"
                                                    placeholder="enrollment key"
                                                    value={
                                                        this.state.enrollmentKey
                                                    }
                                                    onChange={
                                                        this.onChangeEnrollmentKey
                                                    }
                                                />
                                            </div>

                                            <div className="form-group">
                                                <button
                                                    className="btn btn-primary btn-block"
                                                    style={{
                                                        marginBottom: '20px'
                                                    }}
                                                >
                                                    Enroll
                                                </button>
                                            </div>
                                        </div>
                                    )}

                                    {this.state.message && (
                                        <div className="form-group">
                                            <div
                                                className={
                                                    this.state.successful
                                                        ? 'alert alert-success'
                                                        : 'alert alert-danger'
                                                }
                                                role="alert"
                                            >
                                                {this.state.message}
                                            </div>
                                        </div>
                                    )}
                                    <CheckButton style={{ display: 'none' }} />
                                </Form>
                            </div>
                        ) : (
                            <div className="create-course-box">
                                <Link
                                    className="create-course"
                                    to="/courses/create"
                                >
                                    create course
                                </Link>
                            </div>
                        )}
                        {this.state.message && (
                            <div className="form-group">
                                <div
                                    className={
                                        this.state.successful
                                            ? 'alert alert-success'
                                            : 'alert alert-danger'
                                    }
                                    role="alert"
                                >
                                    {this.state.message}
                                </div>
                            </div>
                        )}
                        <Form
                            className="search-input"
                            onSubmit={this.filterCourses}
                        >
                            {!this.state.successful && (
                                <div>
                                    <div className="form-group search-input-field">
                                        <Input
                                            type="text"
                                            className="form-control"
                                            name="name"
                                            placeholder="search..."
                                            value={this.state.input}
                                            onChange={this.onChangeInput}
                                        />
                                    </div>

                                    <div className="form-group">
                                        <button
                                            className="btn btn-primary btn-block"
                                        >
                                            Find
                                        </button>
                                    </div>
                                </div>
                            )}

                            {this.state.message && (
                                <div className="form-group">
                                    <div
                                        className={
                                            this.state.successful
                                                ? 'alert alert-success'
                                                : 'alert alert-danger'
                                        }
                                        role="alert"
                                    >
                                        {this.state.message}
                                    </div>
                                </div>
                            )}
                            <CheckButton style={{ display: 'none' }} />
                        </Form>
                        {this.state.filteredCourses.map((course, index) => {
                            return (
                                <CoursePreview key={index} course={course} />
                            );
                        })}
                    </div>
                )}
            </div>
        );
    }
}
