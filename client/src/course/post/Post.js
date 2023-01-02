import {React} from 'react';
import {useNavigate, useParams} from "react-router-dom";


export default function Post() {
    const {id} = useParams();
    const navigate = useNavigate();

    const handleClick = () => {
        navigate(-1);
    }

    return (
        <div>
            <h1>hello from Miao, {id}</h1>
            <button onClick={handleClick}>back</button>
        </div>
    );

}