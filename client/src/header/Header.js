import { React } from 'react';
import { useNavigate } from 'react-router-dom';
import Menu from '../menu/Menu';
import './index.css';

const Header = () => {
    let navigate = useNavigate();

    return (
        <header className="header-class">
            <div className="header-app-box header-app-name">
                <p className="header-app-name-title">Classroomy</p>
            </div>
            <button
                type="button"
                className="header-app-box header-app-my-courses"
                onClick={() => navigate('/')}
            >
                <p className="header-app-name-title">My Courses</p>
            </button>
            <Menu />
        </header>
    );
};

export default Header;
