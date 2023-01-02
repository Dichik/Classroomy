import { React } from 'react';
import { Link } from 'react-router-dom';
import './index.css';

export default function Header() {
    return (
        <header>
            <h1 id="nav-title"><Link className='title-link' to={"/"}>classroomy</Link></h1>
            <nav>
                <ul>
                    <li>
                        <Link to="/courses">my courses</Link>
                    </li>
                    <li>
                        <Link to="/courses/:id/deadlines">homeworks</Link>
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