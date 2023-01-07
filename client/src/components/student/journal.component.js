import React, { Component } from 'react';
import { Spinner } from 'reactstrap';
import { withRouter } from '../../common/with-router';
import studentService from '../../services/student.service';
import './index.css';

class Journal extends Component {
    constructor(props) {
        super(props);

        this.state = {
            students: [],
            loading: false
        };
    }

    componentDidMount() {
        this.setState({
            loading: true
        });
        const id = this.props.router.location.pathname.split('/')[2];
        studentService
            .getStudentsByCourseId(id)
            .then((response) => response.data)
            .then((data) => {
                this.setState({
                    students: data,
                    loading: false
                });
            });
    }

    render() {
        return (
            <div>
                <h1>hello</h1>
                {this.state.loading ? (
                    <Spinner />
                ) : (
                    <div className="deadlines-table">
                        <table
                            style={{
                                border: '1px solid #333'
                            }}
                        >
                            <thead>
                                <tr>
                                    <th colSpan={2}>Students</th>
                                </tr>
                            </thead>
                            <tbody>
                                <tr>
                                    <td>Username</td>
                                    <td>Total mark</td>
                                </tr>
                                {this.state.students.map((student, index) => {
                                    return (
                                        <tr key={index}>
                                            <td>{student.username}</td>
                                            <td>0.0</td>
                                        </tr>
                                    );
                                })}
                            </tbody>
                        </table>
                    </div>
                )}
            </div>
        );
    }
}

export default withRouter(Journal);
