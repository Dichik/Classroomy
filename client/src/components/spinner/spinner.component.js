import { Component, React } from 'react';
import { Audio } from 'react-loader-spinner';
import styled from 'styled-components';

const ContainerLoader = styled.div`
    position: fixed;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
`;

const reloadPage = () => {
    window.location.reload();
};

export default class Spinner extends Component {
    render() {
        return (
            <ContainerLoader>
                <Audio
                    height="80"
                    width="80"
                    radius="9"
                    color="green"
                    ariaLabel="loading"
                    wrapperStyle
                    wrapperClass
                />
                <div>
                    <p>Success!</p>
                    <button onClick={reloadPage}>Ok!</button>
                </div>
            </ContainerLoader>
        );
    }
}
