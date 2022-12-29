import { React } from 'react';
import { Link } from 'react-router-dom';
import './index.css';

const Home = () => {

    return (
        <div>
            <h1>welcome to classroomy!</h1>
            <h2>here your journey starts</h2>
            <Link to={"/courses"}>see my courses</Link>
        </div>
    );
};

export default Home;