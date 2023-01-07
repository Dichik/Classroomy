// import React, { Component } from 'react';
// import { Navigate } from 'react-router-dom';
// import courseService from '../services/course.service';

// export default class Main extends Component {
//     constructor(props) {
//         super(props);

//         this.state = {
//             redirect: null,
//             userReady: false,
//             currentUser: { username: '' }
//         };
//     }

//     componentDidMount() {
//         this.onChangeLoading;
//         courseService
//             .getCourses()
//             .then((response) => response.data)
//             .then((data) => {
//                 console.log(data);
//                 this.onChangeCourses(data);
//                 this.onChangeLoading();
//             });
//     }

//     render() {
//         if (this.state.redirect) {
//             return <Navigate to={this.state.redirect} />;
//         }

//         const { currentUser } = this.state;

//         return (
//             <div className="container">
//                 {this.state.userReady ? (
//                     <div>
//                         <header className="jumbotron">
//                             <h3>
//                                 <strong>{currentUser.username}</strong> Profile
//                             </h3>
//                         </header>
//                         <p>
//                             <strong>Token:</strong>{' '}
//                             {currentUser.accessToken.substring(0, 20)} ...{' '}
//                             {currentUser.accessToken.substr(
//                                 currentUser.accessToken.length - 20
//                             )}
//                         </p>
//                         <p>
//                             <strong>Id:</strong> {currentUser.id}
//                         </p>
//                         <p>
//                             <strong>Email:</strong> {currentUser.email}
//                         </p>
//                         <strong>Authorities:</strong>
//                         <ul>
//                             {currentUser.roles &&
//                                 currentUser.roles.map((role, index) => (
//                                     <li key={index}>{role}</li>
//                                 ))}
//                         </ul>
//                     </div>
//                 ) : null}
//             </div>
//         );
//     }
// }

import React, { Component } from 'react';
import { Link } from 'react-router-dom';
import CoursePreview from './course/course.preview.component';
import Spinner from './spinner/spinner.component';
import courseService from '../services/course.service';
import authService from '../services/auth.service';

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

    enrollToCourse = () =>  {
        courseService
            .enrollToCourse(this.state.enrollmentKey)
            .then((response) => response.data)
            .then((data) => {
                console.log(data);
                this.setState({
                    message: data
                })
            });
    };

    filterCourses = () => {
        if (this.state.input === '') {
            this.setState({
                filteredCourses: this.state.courses
            });
            return;
        }
        const courses = [];
        this.state.courses.forEach((course) => {
            if (course.name === this.state.input) {
                courses.push(course);
            }
        });
        this.setState({
            filteredCourses: courses
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
                                <input
                                    value={this.state.enrollmentKey}
                                    type="text"
                                    placeholder="enrollment key"
                                    onChange={this.onChangeEnrollmentKey}
                                />
                                <button
                                    type="button"
                                    onClick={this.enrollToCourse}
                                >
                                    Enroll
                                </button>
                            </div>
                        ) : (
                            <Link to="/courses/create">create course</Link>
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
                        <form
                            style={{
                                marginTop: '20px'
                            }}
                        >
                            <input
                                value={this.state.input}
                                type="text"
                                placeholder="search..."
                                onChange={this.onChangeInput}
                            />
                            <button type="button" onClick={this.filterCourses}>
                                Find
                            </button>
                        </form>
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
