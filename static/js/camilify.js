var keys = [];
keys.push(Array.from('qwertyuiop'))
keys.push(Array.from('asdfghjkl'))
keys.push(Array.from('zxcvbnm,.'))

function getRandomInt(min, max) {
  return Math.floor(Math.random() * (max - min)) + min;
}

function choice(ar) {
    return ar[getRandomInt(0, ar.length)];
}

function getCoords(key) {
    var fi = -1;
    for(var i = 0; i < keys.length; i++) {
        var j = keys[i].indexOf(key);
        if(j != -1) {
            fi = i;
            break;
        }
    }

    if(fi == -1) {
        return false;
    } else {
        return [fi, j]
    }
}

function clamp(val, min, max) {
    if(val < min) {
        return min;
    } else if(val > max) {
        return max;
    } else {
        return val;
    }
}

function camilify(strong, deviate, repeat) {
    var deviate_x = deviate || 0.2;
    var deviate_y = deviate_x / 2.0;
    repeat = repeat || 0.05;

    var output = [];
    var coord = [];
    while(strong) {
        var key = strong[0];
        coords = getCoords(key);
        if(coords) {
            if(Math.random() < deviate_x) {
                new_x = coords[1] + choice([-1, 1]);
            } else {
                new_x = coords[1];
            }

            if(Math.random() < deviate_y) {
                new_y = coords[0] + choice([-1, 1]);
            } else {
                new_y = coords[0];
            }

            new_y = clamp(new_y, 0, keys.length - 1);
            new_x = clamp(new_x, 0, keys[new_y].length - 1);
            new_coords = [new_y, new_x];
            output.push(keys[new_coords[0]][new_coords[1]]);
        } else {
            output.push(key);
        }

        if(Math.random() > repeat) {
            strong = strong.slice(1);
        }
    }

    return output.join("");
}
