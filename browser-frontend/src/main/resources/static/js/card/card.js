class Card {

    constructor({
        user,
        onDismiss,
        onLike,
        onDislike
    }) {
        this.user = user;
        this.onDismiss = onDismiss;
        this.onLike = onLike;
        this.onDislike = onDislike;
        this.#init();
    }

    // private properties
    #startPoint;
    #offsetX;
    #offsetY;

    #calcAge(yearDobString) {
        var dateDob = Date.parse(yearDobString);
        var currDate = Date.now();
        var diff = currDate - dateDob;   
        var year = Math.trunc(diff / (1000 * 60 * 60 * 24 * 365));
        return year;
    }

    // private methods
    #init = () => {

        /* init field */
        let usernameUser = this.user.username;
        let fullNameUser = this.user.fullName;
        let phoneUser = this.user.phone;
        let descriptionUser = this.user.description;
        let dobUser = this.user.dob;
        let pathImgUser = this.user.pathImg;

        /*console.log(`
            usernameUser = ${usernameUser},
            fullNameUser = ${fullNameUser},
            phoneUser = ${phoneUser},
            descriptionUser = ${descriptionUser},
            dobUser = ${dobUser},
            pathImgUser = ${pathImgUser}
        `);*/

        let fullNameUserCard = (fullNameUser === null || fullNameUser === "null" || fullNameUser === '') ? "No Name" : fullNameUser;
        let ageUserCard = (dobUser === null || dobUser === "null" || dobUser === '') ? "??" : this.#calcAge(dobUser);
        let descriptionCard = (descriptionUser === null || descriptionUser === "null" || descriptionUser === '') ? "" : descriptionUser;
        
        const card = document.createElement('div');
        card.classList.add('card');

        /* fullName на card */
        const spanFullName = document.createElement('span');
        spanFullName.style.cssText += `
                                font-size: 5vh;
                                margin-top: 44vh;
                                position: absolute;
                                margin-left: 28px;
                                font-family: 'Wix Madefor Text', sans-serif;  
                                font-weight: 800;
                                color: white;
                                transition: all .5s;
                                text-shadow: black 0.1em 0.1em 0.2em;`;
        const fullName = document.createTextNode(fullNameUserCard + " ");
        spanFullName.appendChild(fullName);
        /* age (fullName) */
        const spanAge = document.createElement('span');
        spanAge.style.cssText += `
                                font-weight: 100;
                                font-size: 4vh;
                                transition: all 0.5s ease 0s;
                                text-shadow: black 0.1em 0.1em 0.2em;`;
        const age = document.createTextNode(ageUserCard);
        spanAge.appendChild(age);
        spanFullName.appendChild(spanAge);

        card.append(spanFullName);

        /* active блок */
        const divActive = document.createElement('div');
        divActive.style.cssText += `
                                    border-radius: 20px;
                                    padding: 1.2vh;
                                    background-color: #57fa80;
                                    color: black;
                                    position: absolute;
                                    margin-left: 28px;
                                    margin-top: 40vh;
                                    font-family: sans-serif;
                                    font-size: 1.5vh;
                                    font-weight: bold;
        `;
        const textDivActive = document.createTextNode("Недавняя активность");
        divActive.appendChild(textDivActive);
        card.append(divActive);

        /* description на card */
        if (descriptionCard.length > 200) {
            descriptionCard = descriptionCard.substring(0,200) + "...";
            // console.log(descriptionCard);
        } 
        
        const spanDescription = document.createElement('span');
        spanDescription.style.cssText += `
                                font-size: 2.0vh;
                                margin-top: 51vh;
                                position: absolute;
                                margin-left: 28px;
                                font-family: 'Wix Madefor Text', sans-serif;  
                                font-weight: 400;
                                color: white;
                                transition: all .5s;
                                text-shadow: black 0.1em 0.1em 0.2em;`;
        const textDescription = document.createTextNode(descriptionCard);
        spanDescription.appendChild(textDescription);
        card.append(spanDescription);

        const img = document.createElement('img');
        // img.src = this.imageUrl;
        img.src = pathImgUser;
        card.append(img);

        this.element = card;
        /* event card */
        this.#listenToMouseEvents();
        this.#listenToTouchEvents();
    }


    /* mouse events */
    #listenToMouseEvents = () => {

        // mousedown
        this.element.addEventListener('mousedown', (event) => {

            const { clientX, clientY } = event;
            this.#startPoint = { x: clientX, y: clientY };
            this.element.style.transition = '';
            document.addEventListener('mousemove', this.#handleMouseMove);
        });

        // mouseup
        document.addEventListener('mouseup', this.#handleMouseUp);

        // prevent drag
        this.element.addEventListener('dragstart', (event) => {
            event.preventDefault();
        });
    }

    #handleMouseMove = (event) => {

        if (!this.#startPoint) return;

        const { clientX, clientY } = event;
        this.#offsetX = clientX - this.#startPoint.x;
        this.#offsetY = clientY - this.#startPoint.y;

        const rotate = this.#offsetX * 0.1;

        /* перемещение и вращение card */
        this.element.style.transform = `
                                        translate(${this.#offsetX}px,${this.#offsetY}px)
                                        rotate(${rotate}deg)
        `;

        /* пропустить */
        if (Math.abs(this.#offsetX) > this.element.clientWidth * 0.7) {

            const direction = this.#offsetX > 0 ? 1 : -1;
            this.#dismiss(direction);
        }
    }

    #handleMouseUp = (event) => {

        this.#startPoint = null;
        document.removeEventListener('mousemove', this.#handleMouseMove);
        this.element.style.transform = '';
        this.element.style.transition = 'transform 0.5s';
    }

    dismissSwipe(direction) {
        
        this.#startPoint = null;
        this.offsetY = -20;

        document.removeEventListener('mouseup', this.#handleMouseUp);
        document.removeEventListener('mousemove', this.#handleMouseMove);

        this.element.style.transition = 'transform 1s';
        this.element.style.transform = `translate(${direction * window.innerWidth}px, ${this.offsetY}px)
            rotate(${90 * direction}deg)
        `;

        this.element.classList.add('dismissing');

        setTimeout(() => {
            this.element.remove();
        }, 1000);

        if (typeof this.onDismiss === 'function') {
            this.onDismiss();
        }

        if (typeof this.onLike === 'function' && direction === 1) {
            this.onLike();
        }

        if (typeof this.onDislike === 'function' && direction === -1) {
            this.onDislike();
        }
    }

    #dismiss = (direction) => {

        this.#startPoint = null;

        document.removeEventListener('mouseup', this.#handleMouseUp);
        document.removeEventListener('mousemove', this.#handleMouseMove);

        document.removeEventListener('touchend', this.#handleTouchUp);
        document.removeEventListener('touchmove', this.#handleTouchMove);


        this.element.style.transition = 'transform 1s';
        this.element.style.transform = `translate(${direction * window.innerWidth}px, ${this.offsetY}px)
            rotate(${90 * direction}deg)
        `;

        this.element.classList.add('dismissing');

        setTimeout(() => {
            this.element.remove();
        }, 1000);

        if (typeof this.onDismiss === 'function') {
            this.onDismiss();
        }

        if (typeof this.onLike === 'function' && direction === 1) {
            this.onLike();
        }

        if (typeof this.onDislike === 'function' && direction === -1) {
            this.onDislike();
        }
    }

    /* touch events */
    #listenToTouchEvents = () => {

            // mousedown -> touchstart
            this.element.addEventListener('touchstart', (event) => {

                const { clientX, clientY } = event.touches[0];
                this.#startPoint = { x: clientX, y: clientY };
                this.element.style.transition = '';
                // mousemove -> touchmove
                document.addEventListener('touchmove', this.#handleTouchMove);
            });
            // mouseup -> touchend
            document.addEventListener('touchend', this.#handleTouchUp);
            // prevent drag
            this.element.addEventListener('dragstart', (event) => {
                event.preventDefault();
            });
        }

        #handleTouchMove = (event) => {

            if (!this.#startPoint) return;

            const { clientX, clientY } = event.touches[0];
            this.#offsetX = clientX - this.#startPoint.x;
            this.#offsetY = clientY - this.#startPoint.y;

            const rotate = this.#offsetX * 0.1;

            /* перемещение и вращение card */
            this.element.style.transform = `
                                            translate(${this.#offsetX}px,${this.#offsetY}px)
                                            rotate(${rotate}deg)
            `;

            /* пропустить */
            if (Math.abs(this.#offsetX) > this.element.clientWidth * 0.7) {

                const direction = this.#offsetX > 0 ? 1 : -1;
                this.#dismissTouch(direction);
            }
        }

        #handleTouchUp = (event) => {

            this.#startPoint = null;
            document.removeEventListener('touchmove', this.#handleTouchMove);
            this.element.style.transform = '';
            this.element.style.transition = 'transform 0.5s';
        }

        #dismissTouch = (direction) => {

            this.#startPoint = null;

            document.removeEventListener('touchend', this.#handleTouchUp);
            document.removeEventListener('touchmove', this.#handleTouchMove);

            this.element.style.transition = 'transform 1s';
            this.element.style.transform = `translate(${direction * window.innerWidth}px, ${this.offsetY}px)
                rotate(${90 * direction}deg)
            `;

            this.element.classList.add('dismissing');

            setTimeout(() => {
                this.element.remove();
            }, 1000);

            if (typeof this.onDismiss === 'function') {
                this.onDismiss();
            }

            if (typeof this.onLike === 'function' && direction === 1) {
                this.onLike();
            }

            if (typeof this.onDislike === 'function' && direction === -1) {
                this.onDislike();
            }
        }
}