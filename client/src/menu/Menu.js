import {React} from 'react';
import './index.css';
import menu from '../images/circled-menu.png';

const Menu = () => {

    return (
        <div>
            <img src={menu} alt='menu-icon' className='menu-box' />
        </div>
    )

}

export default Menu;