import React, { Component } from 'react';
import './index.css';
import study from '../../images/study.jpeg';
import { Link } from 'react-router-dom';
import authService from '../../services/auth.service';

export default class Home extends Component {
    constructor(props) {
        super(props);

        this.state = {
            user: undefined
        };
    }

    componentDidMount() {
        const user = authService.getCurrentUser();

        this.setState({
            user: user
        });
    }

    render() {
        const user = this.state.user;

        return (
            <div>
                <div className="main-title">
                    <h2>
                        <b>Welcome to Classroomy</b>
                    </h2>
                    <h4>Dont wait, lets study</h4>
                    <div className="form-group">
                        {!user && (
                            <Link
                                className="btn btn-primary btn-block"
                                style={{
                                    marginTop: '20px'
                                }}
                                to={'/login'}
                            >
                                Login & Start
                            </Link>
                        )}
                        {user && (
                            <Link
                                className="btn btn-primary btn-block"
                                style={{
                                    marginTop: '20px'
                                }}
                                to={'/courses'}
                            >
                                My Courses
                            </Link>
                        )}
                    </div>
                </div>
                <div>
                    <img className="main-image" src={study} />
                </div>
            </div>
        );
    }
}
