import './style.css'
import { build } from './graph'
import { eventHandler } from './handle-event'
import { visualize } from './visualization';

// const inputElem: HTMLElement = document.getElementById('input') as HTMLElement;
// eventHandler(inputElem, (file: File)=>{
//     build(file, visualize);
// });


window.onload = function() {
    window.javaQuery({
        request: 'hello',
        persistent: false,
        onSuccess: function(response: string) {
            build(response, visualize);
        },
        onFailure: function(error_code: number, error_message: string) { }
    })
};

