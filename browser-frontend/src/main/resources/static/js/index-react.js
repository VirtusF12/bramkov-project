
class Header extends React.Component {

    render() {
        return (<header>{this.props.name}</header>);
    }
}

class App extends React.Component {

    render() {

        return (<div>

            <Header name="Информация о пользователе"/>

            <p>FullName</p>
            <p>Коваленко Михаил Валерьевич</p>
            <br />

            <p>Login</p>
            <p>Kovalenko_M</p>
            <br/>

            <p>Description</p>
            <p>Некоторое описание пользоавтеля</p>
            <br /> 

            <a href="/auth"> Назад </a>

        </div>);
    }
}


ReactDOM.render(<App />, document.getElementById("app"));