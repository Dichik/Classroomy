import React, { Component } from 'react';
import { Link } from 'react-router-dom';
// import './index.css';

export default class Header extends Component {
    constructor(props) {
        super(props);
    }

    render() {
        return (
            <header>
                <h1 id="nav-title">
                    <Link className="title-link" to={'/'}>
                        classroomy
                    </Link>
                </h1>
                <nav>
                    <ul>
                        <li>
                            <Link to="/courses">my courses</Link>
                        </li>
                        <li>
                            <Link to={'/posts/deadlines'}>homeworks</Link>
                        </li>
                        <li>
                            <Link to="/users/current">profile</Link>
                        </li>
                        <li>
                            <Link to="/logout">logout</Link>
                        </li>
                    </ul>
                </nav>
            </header>
        );
    }
}
