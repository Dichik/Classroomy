import React, { Component } from 'react';
import Spinner from '../spinner/spinner.component';
import PostService from '../../services/post.service';
import styled from 'styled-components';
import Table from './table.component';
import { withRouter } from '../../common/with-router';

class Deadline extends Component {
    constructor(props) {
        super(props);

        this.state = {
            loading: false,
            posts: []
        };
    }

    componentDidMount() {
        this.setState({
            loading: true
        });
        PostService.getDeadlines().then((res) => {
            console.log(res.data);
            this.setState({
                posts: res.data,
                loading: false
            });
        });
    }

    render() {
        return (
            <div>
                {this.state.loading ? (
                    <Spinner />
                ) : (
                    <div className="deadlines-table">
                        <Styles>
                            <Table data={this.state.posts} />
                        </Styles>
                    </div>
                )}
            </div>
        );
    }
}

export default withRouter(Deadline);

const Styles = styled.div`
    padding: 1rem;

    table {
        border-spacing: 0;
        border: 1px solid black;
        width: 1000px;

        tr {
            :last-child {
                td {
                    border-bottom: 0;
                }
            }
        }

        th,
        td {
            margin: 0;
            padding: 0.5rem;
            border-bottom: 1px solid black;
            border-right: 1px solid black;

            :last-child {
                border-right: 0;
            }
        }
    }
`;
