import { React } from 'react';
import './index.css';

export default function Search({ input, setInput, filterCourses }) {
    const handleInput = (event) => {
        setInput(event.target.value);
    };

    return (
        <form className="search-box">
            <input
                value={input}
                type="text"
                placeholder="search..."
                onChange={handleInput}
            />
            <button
                type="button"
                className="search-button"
                onClick={filterCourses}
            />
        </form>
    );
}
