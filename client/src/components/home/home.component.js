import React, { Component } from 'react';
import './index.css';
import study from '../../images/study.jpeg';
import { Link } from 'react-router-dom';

export default class Home extends Component {
    render() {
        return (
            <div>
                <div className="main-title">
                    <h2>
                        <b>Welcome to Classroomy</b>
                    </h2>
                    <h4>Dont wait, lets study</h4>
                    <div className="form-group">
                        <Link
                            className="btn btn-primary btn-block"
                            style={{
                                marginTop: '20px'
                            }}
                            to={'/login'}
                        >
                            Login & Start
                        </Link>
                    </div>
                </div>
                <div>
                    <img className="main-image" src={study} />
                </div>
            </div>
        );
    }
}
