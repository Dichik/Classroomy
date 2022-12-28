import { React } from 'react';
import { Link } from 'react-router-dom';
import './index.css';

const Header = () => {
    // let navigate = useNavigate();

    return (
        <header>
            <h1 id="nav-title">classroomy</h1>
            <nav>
                <ul>
                    <li>
                        <Link to="/">my courses</Link>
                    </li>
                    <li>
                        <a href="#">homeworks</a>
                    </li>
                    <li>
                        <a href="#">profile</a>
                    </li>
                    <li>
                        <a href="#">logout</a>
                    </li>
                </ul>
            </nav>
        </header>
    );
};

export default Header;
