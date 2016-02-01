/**
 * used like java.lang.HashMap
 */


function HashMap() {
  private:
          this.len = 8;
  this.table = new Array();
  this.length = 0;
  this.hash = hash;
  function hash(x) {
    var h = x.hashCode();
    h += ~(h << 9);
    h ^= (h >>> 14);
    h += (h << 4);
    h ^= (h >>> 10);
    return h;
  }

  this.rehash = rehash;
  function rehash() {

    var oldTable = this.table;

    this.table = new Array();

    //transfer
    for (var i = 0; i < oldTable.length; i++) {
      var e = oldTable[i];
      if (e != null) {
        oldTable[i] = null;
        do {
          var next = e.next;
          var j = this.indexFor(e.hash);
          e.next = this.table[j];
          this.table[j] = e;
          e = next;
        } while (e != null);
      }
    }
    //alert("rehash to :"+this.len);
  }


  this.indexFor = indexFor;
  function indexFor(h) {

    var index = h & (this.len - 1);
    return index;
  }

  function Entry(h, k, v, n) {

    this.value = v;
    this.next = n;
    this.key = k;
    this.hash = h;

    this.getKey = getKey;
    function getKey() {
      return this.key;
    }

    this.getValue = getValue;
    function getValue() {
      return this.value;
    }
    this.setValue = setValue;
    function setValue(newValue) {
      var oldValue = this.value;
      this.value = newValue;
      return oldValue;
    }

    this.equals = equals;
    function equals(o) {
      var e = o;
      var k1 = this.getKey();
      var k2 = e.getKey();
      var v1 = this.getValue();
      var v2 = e.getValue();
      return (k1.equals(k2) && v1.equals(v2));
    }

    this.hashCode = hashCode;
    function hashCode() {
      return this.key.hashCode() ^ this.value.hashCode();
    }

    this.toString = toString;
    function toString() {
      return this.getKey() + "=" + this.getValue();
    }
  }


  function HashIterator(table, index, ne) {

    this.table = table;
    this.ne = ne;
    this.index = index;
    this.current = null;

    this.hasNext = hasNext;
    function hasNext() {
      return this.ne != null;
    }

    this.next = next;
    function next() {

      var e = this.ne;
      if (e == null)
        throw "No such Element";

      var n = e.next;
      var t = this.table;
      var i = this.index;
      while (n == null && i > 0)
        n = t[--i];
      this.index = i;
      this.ne = n;
      this.current = e;

      return this.current;
    }
  }


  public:
          this.size = size;
  function size() {
    return this.length;
  }


  this.isEmpty = isEmpty;
  function isEmpty() {
    return this.length == 0;
  }

  this.get = get;
  function get(key) {
    var hash = this.hash(key);
    var i = this.indexFor(hash);

    var e = this.table[i];

    while (true) {
      if (e == null)
        return null;
      if (e.hash == hash && key.equals(e.key))
        return e.value;
      e = e.next;
    }

  }

  this.containsKey = containsKey;
  function containsKey(key) {
    var hash = this.hash(key);
    var i = this.indexFor(hash);
    var e = this.table[i];

    while (e != null) {
      if (e.hash == hash && key.equals(e.key))
        return true;
      e = e.next;
    }
    return false;
  }


  this.put = put;
  function put(key, value) {
    var hash = this.hash(key);
    var i = this.indexFor(hash);


    for (var e = this.table[i]; e != null; e = e.next) {
      if (e.hash == hash && key.equals(e.key)) {
        var oldValue = e.value;
        e.value = value;
        return oldValue;
      }
    }

    this.addEntry(hash, key, value, i);

    var r = Math.ceil(this.length * 1.5);

    if (r > this.len) {
      this.len = this.len << 1;
      this.rehash();
    }
    return null;
  }

  this.putAll = putAll;
  function putAll(map) {
    var mod = false;
    for (var it = map.iterator(); it.hasNext();) {
      var e = it.next();
      if (this.put(e.getKey(), e.getValue())) mod = true;
    }
  }


  this.remove = remove;
  function remove(key) {
    var e = this.removeEntryForKey(key);

    return (e == null ? null : e.value);
  }

  this.removeEntryForKey = removeEntryForKey;
  function removeEntryForKey(key) {
    var hash = this.hash(key);
    var i = this.indexFor(hash);

    var prev = this.table[i];
    var e = prev;

    while (e != null) {
      var next = e.next;
      if (e.hash == hash && key.equals(e.key)) {
        this.length--;
        if (prev.equals(e))
          this.table[i] = next;
        else
          prev.next = next;
        return e;
      }
      prev = e;
      e = next;
    }
    return e;
  }

  this.clear = clear;
  function clear() {
    for (var i = 0; i < this.table.length; i++)
      this.table[i] = null;
    this.length = 0;
  }

  this.containsValue = containsValue;
  function containsValue(value) {
    if (value == null) return false;

    var tab = this.table;
    for (var i = 0; i < tab.length; i++)
      for (var e = tab[i]; e != null; e = e.next)
        if (value.equals(e.value))
          return true;
    return false;
  }


  this.addEntry = addEntry;
  function addEntry(hash, key, value, bucketIndex) {
    this.table[bucketIndex] = new Entry(hash, key, value, this.table[bucketIndex]);
    this.length++;
  }


  this.iterator = iterator;
  function iterator() {
    var i = this.table.length;

    var next = null;
    while (i > 0 && next == null) {
      next = this.table[--i];
    }

    return new HashIterator(this.table, i, next);
  }

  this.hashCode = hashCode;
  function hashCode() {
    var h = 0;
    for (var it = this.iterator(); it.hasNext();) {
      h += it.next().hashCode();
    }
    return h;
  }
  this.equals = equals;
  function equals(map) {
    if (map.size() != this.size()) return false;

    for (var it = this.iterator(); it.hasNext();) {

      var e = it.next();
      var key = e.getKey();
      var value = e.getValue();

      if (!value.equals(map.get(key))) return false

    }
    return true;
  }
}