import React, { Component } from 'react';
import { Navigate } from 'react-router-dom';
import AuthService from '../../services/auth.service';
import './index.css';

export default class Profile extends Component {
    constructor(props) {
        super(props);

        this.state = {
            redirect: null,
            userReady: false,
            currentUser: { username: '' }
        };
    }

    componentDidMount() {
        const currentUser = AuthService.getCurrentUser();

        if (!currentUser) this.setState({ redirect: '/home' });
        this.setState({ currentUser: currentUser, userReady: true });
    }

    render() {
        if (this.state.redirect) {
            return <Navigate to={this.state.redirect} />;
        }

        const { currentUser } = this.state;

        return (
            <div className="container">
                {this.state.userReady ? (
                    <div>
                        <header className="jumbotron">
                            <img
                                src="//ssl.gstatic.com/accounts/ui/avatar_2x.png"
                                alt="profile-img"
                                className="profile-img-card"
                            />
                            <h3>
                                <strong>{currentUser.username}</strong>
                            </h3>
                        </header>
                        <div className="profile-info">
                            <p>
                                <h5>
                                    <b>Id:</b>
                                </h5>{' '}
                                {currentUser.id}
                            </p>
                            <p>
                                <h5>
                                    <b>Email:</b>
                                </h5>{' '}
                                {currentUser.email}
                            </p>
                            <p>
                                <h5>
                                    <b>Token:</b>
                                </h5>
                                <div>
                                    {' '}
                                    {currentUser.accessToken.substring(
                                        0,
                                        20
                                    )}{' '}
                                    ...{' '}
                                    {currentUser.accessToken.substr(
                                        currentUser.accessToken.length - 20
                                    )}
                                </div>
                            </p>
                            <p>
                                <h5>
                                    <b>Authorities:</b>
                                </h5>
                                <ul>
                                    {currentUser.roles &&
                                        currentUser.roles.map((role, index) => (
                                            <li key={index}>{role}</li>
                                        ))}
                                </ul>
                            </p>
                        </div>
                    </div>
                ) : null}
            </div>
        );
    }
}
